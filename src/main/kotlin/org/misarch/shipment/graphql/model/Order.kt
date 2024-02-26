package org.misarch.shipment.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import graphql.schema.DataFetchingEnvironment
import org.misarch.shipment.graphql.authorizedUserOrNull
import org.misarch.shipment.graphql.model.connection.ShipmentConnection
import org.misarch.shipment.graphql.model.connection.ShipmentFilter
import org.misarch.shipment.graphql.model.connection.ShipmentOrder
import org.misarch.shipment.persistence.model.ShipmentEntity
import org.misarch.shipment.persistence.repository.ShipmentRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLDescription("The Order of a user.")
@KeyDirective(fields = FieldSet("id"))
class Order(
    id: UUID
) : Node(id) {

    @GraphQLDescription("Get all associated Shipments")
    suspend fun variants(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: ShipmentOrder? = null,
        @GraphQLDescription("Filtering")
        filter: ShipmentFilter? = null,
        @GraphQLIgnore
        @Autowired
        shipmentRepository: ShipmentRepository,
        dfe: DataFetchingEnvironment
    ): ShipmentConnection {
        return ShipmentConnection(
            first,
            skip,
            filter,
            ShipmentEntity.ENTITY.orderId.eq(id),
            orderBy,
            shipmentRepository,
            dfe.authorizedUserOrNull
        )
    }

}