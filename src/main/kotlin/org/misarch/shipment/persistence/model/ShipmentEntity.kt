package org.misarch.shipment.persistence.model

import org.misarch.shipment.graphql.model.Shipment
import org.misarch.shipment.graphql.model.ShipmentStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * Entity for shipments
 *
 * @property status status of the shipment
 * @property shipmentMethodId unique identifier of the shipment method this shipment uses
 * @property addressId unique identifier of the address this shipment is sent to
 * @property id unique identifier of the shipment
 */
@Table
class ShipmentEntity(
    val status: ShipmentStatus,
    val shipmentMethodId: UUID,
    val addressId: UUID,
    @Id
    override val id: UUID?
) : BaseEntity<Shipment> {

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QShipmentEntity.shipmentEntity!!
    }

    override fun toDTO(): Shipment {
        return Shipment(
            id = id!!,
            status = status,
            shipmentMethodId = shipmentMethodId,
            addressId = addressId
        )
    }

}