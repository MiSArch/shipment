package org.misarch.shipment.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.shipment.persistence.model.ShipmentEntity
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [ShipmentEntity]s
 */
@Repository
interface ShipmentRepository : QuerydslR2dbcRepository<ShipmentEntity, UUID> {

    /**
     * Find a [ShipmentEntity] by its return ID.
     *
     * @param returnId the return ID
     * @return the [ShipmentEntity]
     */
    suspend fun findByReturnId(returnId: UUID): ShipmentEntity

}