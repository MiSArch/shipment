package org.misarch.shipment.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import java.util.*

@GraphQLDescription("Item in a CalculateFeesInput")
class CalculateFeesItemInput(
    @property:GraphQLDescription("The product variant version id.")
    val productVariantVersionId: UUID,
    @property:GraphQLDescription("The count of the product variant version.")
    val count: Int
)