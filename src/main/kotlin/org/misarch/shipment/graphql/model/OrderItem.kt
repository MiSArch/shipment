package org.misarch.shipment.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import java.util.*

@GraphQLDescription("An item in an order.")
@KeyDirective(fields = FieldSet("id"), resolvable = false)
class OrderItem(
    id: UUID
) : Node(id)