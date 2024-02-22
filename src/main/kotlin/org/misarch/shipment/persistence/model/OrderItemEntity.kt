package org.misarch.shipment.persistence.model

import org.misarch.shipment.graphql.model.OrderItem
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * Entity for order items
 *
 * @property id unique identifier of the order item
 */
@Table
class OrderItemEntity(
    @Id
    override val id: UUID
) : BaseEntity<OrderItem> {

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QOrderItemEntity.orderItemEntity!!
    }

    override fun toDTO(): OrderItem {
        return OrderItem(id = id)
    }
}