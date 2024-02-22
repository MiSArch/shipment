package org.misarch.shipment.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ShareableDirective
import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.shipment.graphql.model.OrderItem
import org.misarch.shipment.graphql.model.connection.base.BaseConnection
import org.misarch.shipment.graphql.model.connection.base.BaseOrder
import org.misarch.shipment.graphql.model.connection.base.BaseOrderField
import org.misarch.shipment.graphql.model.connection.base.OrderDirection
import org.misarch.shipment.persistence.model.OrderItemEntity
import org.misarch.shipment.persistence.repository.OrderItemRepository
import org.misarch.shipment.graphql.AuthorizedUser

/**
 * A GraphQL connection for [OrderItem]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `OrderItem` values.")
@ShareableDirective
class OrderItemConnection(
    first: Int?,
    skip: Int?,
    predicate: BooleanExpression?,
    order: OrderItemOrder?,
    repository: OrderItemRepository,
    authorizedUser: AuthorizedUser?,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<OrderItem, OrderItemEntity>(
    first,
    skip,
    null,
    predicate,
    (order ?: OrderItemOrder.DEFAULT).toOrderSpecifier(OrderItemOrderField.ID),
    repository,
    OrderItemEntity.ENTITY,
    authorizedUser,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = OrderItemEntity.ENTITY.id
}

@GraphQLDescription("OrderItem order fields")
enum class OrderItemOrderField(override vararg val expressions: Expression<out Comparable<*>>) : BaseOrderField {
    @GraphQLDescription("Order order items by their id")
    ID(OrderItemEntity.ENTITY.id)
}

@GraphQLDescription("OrderItem order")
class OrderItemOrder(
    direction: OrderDirection?, field: OrderItemOrderField?
) : BaseOrder<OrderItemOrderField>(direction, field) {

    companion object {
        val DEFAULT = OrderItemOrder(OrderDirection.ASC, OrderItemOrderField.ID)
    }
}