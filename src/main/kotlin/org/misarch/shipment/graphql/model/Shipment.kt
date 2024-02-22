package org.misarch.shipment.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import graphql.schema.DataFetchingEnvironment
import org.misarch.shipment.graphql.authorizedUserOrNull
import org.misarch.shipment.graphql.dataloader.ShipmentMethodDataLoader
import org.misarch.shipment.graphql.model.connection.OrderItemConnection
import org.misarch.shipment.graphql.model.connection.OrderItemOrder
import org.misarch.shipment.persistence.model.OrderItemEntity
import org.misarch.shipment.persistence.model.ShipmentToOrderItemEntity
import org.misarch.shipment.persistence.repository.OrderItemRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import java.util.concurrent.CompletableFuture

@GraphQLDescription("A shipment caused by either an order or a return.")
@KeyDirective(fields = FieldSet("id"))
class Shipment(
    id: UUID,
    @property:GraphQLDescription("The status of the shipment.")
    val status: ShipmentStatus,
    private val shipmentMethodId: UUID,
    private val addressId: UUID
) : Node(id) {

    @GraphQLDescription("The shipment method this shipment uses.")
    fun shipmentMethod(
        dfe: DataFetchingEnvironment
    ): CompletableFuture<ShipmentMethod> {
        return dfe.getDataLoader<UUID, ShipmentMethod>(ShipmentMethodDataLoader::class.simpleName!!)
            .load(shipmentMethodId, dfe)
    }

    @GraphQLDescription("Get all associated OrderItems")
    suspend fun orderItems(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: OrderItemOrder? = null,
        @GraphQLIgnore
        @Autowired
        orderItemRepository: OrderItemRepository,
        dfe: DataFetchingEnvironment
    ): OrderItemConnection {
        return OrderItemConnection(
            first, skip, ShipmentToOrderItemEntity.ENTITY.shipmentId.eq(id), orderBy, orderItemRepository, dfe.authorizedUserOrNull
        ) {
            it.innerJoin(ShipmentToOrderItemEntity.ENTITY)
                .on(ShipmentToOrderItemEntity.ENTITY.orderItemId.eq(OrderItemEntity.ENTITY.id))
        }
    }

}