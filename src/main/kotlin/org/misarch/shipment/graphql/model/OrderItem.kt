package org.misarch.shipment.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import graphql.schema.DataFetchingEnvironment
import org.misarch.shipment.graphql.dataloader.ShipmentDataLoader
import java.util.*
import java.util.concurrent.CompletableFuture

@GraphQLDescription("An item in an order.")
@KeyDirective(fields = FieldSet("id"))
class OrderItem(
    id: UUID,
    private val sentWithId: UUID?
) : Node(id) {

    @GraphQLDescription("The shipment this order item was originally sent with.")
    fun sentWith(
        dfe: DataFetchingEnvironment
    ): CompletableFuture<Shipment?> {
        if (sentWithId == null) {
            return CompletableFuture.completedFuture(null)
        }
        return dfe.getDataLoader<UUID, Shipment>(ShipmentDataLoader::class.simpleName!!)
            .load(sentWithId, dfe)
    }

}