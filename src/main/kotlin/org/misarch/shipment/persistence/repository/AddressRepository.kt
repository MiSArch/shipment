package org.misarch.shipment.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.selects.select
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
    @Query(
        """
        INSERT INTO AddressEntity
        (id, userId, street1, street2, city, postalCode, country, companyName)
        VALUES (:id, :userId, :street1, :street2, :city, :postalCode, :country, :companyName)
        """
    )
    suspend fun createAddress(
        @Param("id")
        id: UUID,
        @Param("userId")
        userId: UUID?,
        @Param("street1")
        street1: String,
        @Param("street2")
        street2: String,
        @Param("city")
        city: String,
        @Param("postalCode")
        postalCode: String,
        @Param("country")
        country: String,
        @Param("companyName")
        companyName: String?
    )

}

/**
 * Find the current vendor address
 *
 * @return the current vendor address or null if there is no current vendor address
 */
suspend fun AddressRepository.findCurrentVendorAddress(): AddressEntity? {
    return query {
        val entity = AddressEntity.ENTITY
        it.select(entityProjection())
            .from(entity)
            .where(entity.userId.isNull)
            .orderBy(entity.version.desc())
            .limit(1)
    }.first().awaitSingleOrNull()
}