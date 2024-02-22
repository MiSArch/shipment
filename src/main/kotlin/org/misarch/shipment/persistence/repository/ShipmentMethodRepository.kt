package org.misarch.shipment.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.shipment.persistence.model.ShipmentMethodEntity
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [ShipmentMethodEntity]s
 */
@Repository
interface ShipmentMethodRepository : QuerydslR2dbcRepository<ShipmentMethodEntity, UUID>