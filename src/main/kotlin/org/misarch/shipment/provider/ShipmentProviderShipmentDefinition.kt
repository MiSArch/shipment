package org.misarch.shipment.provider

import java.util.UUID

/**
 * Entity for the shipment definition sent to the shipment provider on shipment creation
 *
 * @property id id of the shipment
 * @property ref reference of shipment method used for the shipment
 * @property quantity the number of items in the shipment
 * @property weight weight of the shipment in kg
 */
data class ShipmentProviderShipmentDefinition(
    val id: UUID,
    val ref: String,
    val quantity: Int,
    val weight: Double,
)
