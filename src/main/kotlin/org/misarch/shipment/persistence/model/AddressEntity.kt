package org.misarch.shipment.persistence.model

import org.misarch.shipment.graphql.model.Address
import org.misarch.shipment.graphql.model.UserAddress
import org.misarch.shipment.graphql.model.VendorAddress
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * Entity for addresses
 *
 * @property userId unique identifier of the user, null if the address is a vendor address
 * @property id unique identifier of the address
 */
@Table
class AddressEntity(
    val userId: UUID?,
    @Id
    override val id: UUID
) : BaseEntity<Address> {

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QAddressEntity.addressEntity!!
    }

    override fun toDTO(): Address {
        if (userId == null) {
            return VendorAddress(id)
        } else {
            return UserAddress(id)
        }
    }
}