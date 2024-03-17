package org.misarch.shipment.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.InaccessibleDirective
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import org.misarch.shipment.graphql.dataloader.ShipmentMethodDataLoader
import org.misarch.shipment.graphql.input.CalculateShipmentFeesInput
import org.misarch.shipment.graphql.model.ShipmentMethod
import org.misarch.shipment.graphql.model.connection.ShipmentMethodConnection
import org.misarch.shipment.graphql.model.connection.ShipmentMethodFilter
import org.misarch.shipment.graphql.model.connection.ShipmentMethodOrder
import org.misarch.shipment.persistence.repository.ShipmentMethodRepository
import org.misarch.shipment.service.ShipmentMethodService
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Defines GraphQL queries
 *
 * @property shipmentMethodService service for [ShipmentMethod]s
 * @property shipmentMethodRepository repository for [ShipmentMethod]s
 */
@Component
class Query(
    private val shipmentMethodService: ShipmentMethodService,
    private val shipmentMethodRepository: ShipmentMethodRepository,
) : Query {

    @GraphQLDescription("Get all shipmentMethods")
    suspend fun shipmentMethods(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: ShipmentMethodOrder? = null,
        @GraphQLDescription("Filtering")
        filter: ShipmentMethodFilter? = null,
        dfe: DataFetchingEnvironment
    ): ShipmentMethodConnection {
        return ShipmentMethodConnection(first, skip, filter, null, orderBy, shipmentMethodRepository, dfe.authorizedUserOrNull)
    }

    @GraphQLDescription("Get a shipmentMethod by id")
    fun shipmentMethod(
        @GraphQLDescription("The id of the shipmentMethod")
        id: UUID,
        dfe: DataFetchingEnvironment
    ): CompletableFuture<ShipmentMethod> {
        return dfe.getDataLoader<UUID, ShipmentMethod>(ShipmentMethodDataLoader::class.simpleName!!).load(id)
    }

    @GraphQLDescription("Calculates the fees for a collection of shipped items.")
    @InaccessibleDirective
    suspend fun calculateShipmentFees(
        @GraphQLDescription("The input for the calculateShipmentFees query.")
        input: CalculateShipmentFeesInput
    ): Int {
        return shipmentMethodService.calculateShipmentFees(input)
    }

}