package org.misarch.shipment.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ShareableDirective
import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.shipment.graphql.AuthorizedUser
import org.misarch.shipment.graphql.model.Shipment
import org.misarch.shipment.graphql.model.ShipmentMethod
import org.misarch.shipment.graphql.model.connection.base.*
import org.misarch.shipment.persistence.model.ShipmentMethodEntity
import org.misarch.shipment.persistence.repository.ShipmentMethodRepository

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
@GraphQLDescription("A connection to a list of `ShipmentMethod` values.")
@ShareableDirective
class ShipmentMethodConnection(
    first: Int?,
    skip: Int?,
    filter: ShipmentMethodFilter?,
    predicate: BooleanExpression?,
    order: ShipmentMethodOrder?,
    repository: ShipmentMethodRepository,
    authorizedUser: AuthorizedUser?,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<ShipmentMethod, ShipmentMethodEntity>(
    first,
    skip,
    filter,
    predicate,
    (order ?: ShipmentMethodOrder.DEFAULT).toOrderSpecifier(ShipmentMethodOrderField.ID),
    repository,
    ShipmentMethodEntity.ENTITY,
    authorizedUser,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = ShipmentMethodEntity.ENTITY.id

}

@GraphQLDescription("Shipment method order fields")
enum class ShipmentMethodOrderField(override vararg val expressions: Expression<out Comparable<*>>) : BaseOrderField {
    @GraphQLDescription("Order shipment methods by their id")
    ID(ShipmentMethodEntity.ENTITY.id),


}

@GraphQLDescription("Shipment method order")
class ShipmentMethodOrder(
    direction: OrderDirection?, field: ShipmentMethodOrderField?
) : BaseOrder<ShipmentMethodOrderField>(direction, field) {

    companion object {
        val DEFAULT = ShipmentMethodOrder(OrderDirection.ASC, ShipmentMethodOrderField.ID)
    }
}

@GraphQLDescription("Shipment method filter")
class ShipmentMethodFilter(
    val isArchived: Boolean?
) : BaseFilter {

    override fun toExpression(): BooleanExpression? {
        return if (isArchived != null) {
            ShipmentMethodEntity.ENTITY.archivedAt.let {
                if (isArchived) it.isNotNull else it.isNull
            }
        } else {
            null
        }
    }

}