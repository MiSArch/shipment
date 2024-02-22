package org.misarch.shipment.event.model

import java.util.*

/**
 * Address DTO used for events
 *
 * @property id id of the address
 * @property userId id of the user
 */
data class UserAddressDTO(
    val id: UUID,
    val userId: UUID
)