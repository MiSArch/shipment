package org.misarch.shipment.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.shipment.persistence.model.ProductVariantVersionEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [ProductVariantVersionEntity]s
 */
@Repository
interface ProductVariantVersionRepository : QuerydslR2dbcRepository<ProductVariantVersionEntity, UUID> {

    /**
     * Create a new product variant version
     *
     * @param id unique identifier of the product variant version
     * @param weight weight of the product variant version in kg
     */
    @Modifying
    @Query("INSERT INTO ProductVariantVersionEntity (id, weight) VALUES (:id, :weight)")
    suspend fun createProductVariantVersion(
        @Param("id")
        id: UUID,
        @Param("weight")
        weight: Double
    )

}