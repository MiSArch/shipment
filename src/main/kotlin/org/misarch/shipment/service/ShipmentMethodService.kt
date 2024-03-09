package org.misarch.shipment.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.shipment.event.EventPublisher
import org.misarch.shipment.event.ShipmentEvents
import org.misarch.shipment.event.model.ArchiveShipmentMethodDTO
import org.misarch.shipment.graphql.input.ArchiveShipmentMethodInput
import org.misarch.shipment.graphql.input.CreateShipmentMethodInput
import org.misarch.shipment.graphql.input.ProductVariantVersionWithQuantityInput
import org.misarch.shipment.persistence.model.ShipmentMethodEntity
import org.misarch.shipment.persistence.repository.AddressRepository
import org.misarch.shipment.persistence.repository.ProductVariantVersionRepository
import org.misarch.shipment.persistence.repository.ShipmentMethodRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

/**
 * Service for [ShipmentMethodEntity]s
 *
 * @param repository the provided repository
 * @param addressRepository the user repository
 * @param productVariantVersionRepository the product variant version repository
 * @param eventPublisher the event publisher
 */
@Service
class ShipmentMethodService(
    repository: ShipmentMethodRepository,
    private val addressRepository: AddressRepository,
    private val productVariantVersionRepository: ProductVariantVersionRepository,
    private val eventPublisher: EventPublisher
) : BaseService<ShipmentMethodEntity, ShipmentMethodRepository>(repository) {

    /**
     * Creates a shipment method
     *
     * @param shipmentMethodInput the shipment method to create
     * @return the created shipment method
     */
    suspend fun createShipmentMethod(shipmentMethodInput: CreateShipmentMethodInput): ShipmentMethodEntity {
        val shipmentMethod = ShipmentMethodEntity(
            name = shipmentMethodInput.name,
            description = shipmentMethodInput.description,
            baseFees = shipmentMethodInput.baseFees,
            feesPerItem = shipmentMethodInput.feesPerItem,
            feesPerKg = shipmentMethodInput.feesPerKg,
            externalReference = shipmentMethodInput.externalReference,
            id = null,
            archivedAt = null
        )
        val savedShipmentMethod = repository.save(shipmentMethod).awaitSingle()
        eventPublisher.publishEvent(ShipmentEvents.SHIPMENT_METHOD_CREATED, savedShipmentMethod.toEventDTO())
        return savedShipmentMethod
    }

    /**
     * Archives a shipment method
     *
     * @param archiveShipmentMethodInput defines the shipment method to archive
     * @return the archived shipment method
     */
    suspend fun archiveShipmentMethod(archiveShipmentMethodInput: ArchiveShipmentMethodInput): ShipmentMethodEntity {
        val shipmentMethod = repository.findById(archiveShipmentMethodInput.id).awaitSingle()
        shipmentMethod.archivedAt = OffsetDateTime.now()
        val savedShipmentMethod = repository.save(shipmentMethod).awaitSingle()
        eventPublisher.publishEvent(
            ShipmentEvents.SHIPMENT_METHOD_ARCHIVED, ArchiveShipmentMethodDTO(shipmentMethod.id!!)
        )
        return savedShipmentMethod
    }

    /**
     * Calculates the quantity and weight of a (potential) shipment
     *
     * @param items the items to calculate the quantity and weight for
     * @return the quantity and weight of the items
     */
    suspend fun calculateQuantityAndWeight(
        items: List<ProductVariantVersionWithQuantityInput>,
    ): QuantityAndWeight {
        val productVariantVersions =
            productVariantVersionRepository.findAllById(items.map { it.productVariantVersionId }).collectList()
                .awaitSingle().associateBy { it.id }
        val weight = items.sumOf { productVariantVersions.getValue(it.productVariantVersionId).weight * it.quantity }
        val totalQuantity = items.sumOf { it.quantity }
        return QuantityAndWeight(totalQuantity, weight)
    }

}

/**
 * Quantity of items and weight of a (potential) shipment
 *
 * @property quantity the quantity of items
 * @property weight the weight of the items
 */
data class QuantityAndWeight(
    val quantity: Int, val weight: Double
)