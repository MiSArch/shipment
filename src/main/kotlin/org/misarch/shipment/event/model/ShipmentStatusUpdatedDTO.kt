package org.misarch.shipment.event.model

import org.misarch.shipment.graphql.model.ShipmentStatus
import java.util.UUID

/**
 * DTO for a shipment status update
 *
 * @property id id of the shipment
 * @property status new status of the shipment
 */
data class ShipmentStatusUpdatedDTO(
    val id: UUID,
    val status: ShipmentStatus
)