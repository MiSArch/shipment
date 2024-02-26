package org.misarch.shipment.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ShareableDirective
import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.shipment.graphql.AuthorizedUser
import org.misarch.shipment.graphql.model.Shipment
import org.misarch.shipment.graphql.model.ShipmentStatus
import org.misarch.shipment.graphql.model.connection.base.*
import org.misarch.shipment.persistence.model.ShipmentEntity
import org.misarch.shipment.persistence.repository.ShipmentRepository

/**
 * A GraphQL connection for [Shipment]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param filter The filter to apply to the items
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param authorizedUser The authorized user
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `Shipment` values.")
@ShareableDirective
class ShipmentConnection(
    first: Int?,
    skip: Int?,
    filter: ShipmentFilter?,
    predicate: BooleanExpression?,
    order: ShipmentOrder?,
    repository: ShipmentRepository,
    authorizedUser: AuthorizedUser?,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<Shipment, ShipmentEntity>(
    first,
    skip,
    filter,
    predicate,
    (order ?: ShipmentOrder.DEFAULT).toOrderSpecifier(ShipmentOrderField.ID),
    repository,
    ShipmentEntity.ENTITY,
    authorizedUser,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = ShipmentEntity.ENTITY.id

}

@GraphQLDescription("Shipment  order fields")
enum class ShipmentOrderField(override vararg val expressions: Expression<out Comparable<*>>) : BaseOrderField {
    @GraphQLDescription("Order shipment s by their id")
    ID(ShipmentEntity.ENTITY.id),

}

@GraphQLDescription("Shipment  order")
class ShipmentOrder(
    direction: OrderDirection?, field: ShipmentOrderField?
) : BaseOrder<ShipmentOrderField>(direction, field) {

    companion object {
        val DEFAULT = ShipmentOrder(OrderDirection.ASC, ShipmentOrderField.ID)
    }
}

@GraphQLDescription("Shipment filter")
class ShipmentFilter(
    @GraphQLDescription("Filter shipments by their status")
    val status: ShipmentStatus?
) : BaseFilter {

    override fun toExpression(): BooleanExpression? {
        return if (status != null) {
            ShipmentEntity.ENTITY.status.eq(status)
        } else {
            null
        }
    }

}