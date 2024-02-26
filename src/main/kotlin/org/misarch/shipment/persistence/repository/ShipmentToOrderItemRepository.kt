package org.misarch.shipment.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.shipment.persistence.model.ShipmentToOrderItemEntity
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [ShipmentToOrderItemEntity]s
 */
@Repository
interface ShipmentToOrderItemRepository : QuerydslR2dbcRepository<ShipmentToOrderItemEntity, UUID> {

    /**
     * Finds all [ShipmentToOrderItemEntity]s by the shipment id
     *
     * @param shipmentId the shipment id
     * @return the list of [ShipmentToOrderItemEntity]s
     */
    suspend fun findByShipmentId(shipmentId: UUID): List<ShipmentToOrderItemEntity>

}