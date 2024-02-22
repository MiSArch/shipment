package org.misarch.shipment.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.shipment.persistence.model.AddressEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [AddressEntity]s
 */
@Repository
interface AddressRepository : QuerydslR2dbcRepository<AddressEntity, UUID> {

    /**
     * Create a new address
     *
     * @param id unique identifier of the address
     * @param userId unique identifier of the user, null if the address is a vendor address
     */
    @Modifying
    @Query("INSERT INTO AddressEntity (id, userId) VALUES (:id, :userId)")
    suspend fun createAddress(
        @Param("id")
        id: UUID,
        @Param("userId")
        userId: UUID?
    )

}