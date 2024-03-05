package org.misarch.shipment.event.model.order

/**
 * Defines the shipments delivery and return status.
 */
enum class ShipmentStatus {
    PENDING,
    IN_PROGRESS,
    DELIVERED,
    RETURNED,
    RETURN_IN_PROGRESS
}