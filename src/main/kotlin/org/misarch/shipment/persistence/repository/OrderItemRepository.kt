package org.misarch.shipment.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.shipment.persistence.model.OrderItemEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [OrderItemEntity]s
 */
@Repository
interface OrderItemRepository : QuerydslR2dbcRepository<OrderItemEntity, UUID> {

    /**
     * Create a new order item.
     * If the order item already exists, do nothing.
     *
     * @param id unique identifier of the order item
     */
    @Modifying
    @Query("INSERT INTO OrderItemEntity (id) VALUES (:id) ON CONFLICT DO NOTHING")
    suspend fun createOrderItem(
        @Param("id")
        id: UUID
    )

}