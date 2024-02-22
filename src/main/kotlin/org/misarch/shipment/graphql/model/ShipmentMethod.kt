package org.misarch.shipment.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.shipment.graphql.authorizedUser
import org.misarch.shipment.graphql.input.CalculateFeesItemInput
import org.misarch.shipment.persistence.repository.ProductVariantVersionRepository
import org.springframework.beans.factory.annotation.Autowired
import java.time.OffsetDateTime
import java.util.*

@GraphQLDescription("A shipment method supported by the external provider.")
@KeyDirective(fields = FieldSet("id"))
class ShipmentMethod(
    id: UUID,
    @property:GraphQLDescription("The name of the shipment method.")
    val name: String,
    @property:GraphQLDescription("The description of the shipment method.")
    val description: String,
    @property:GraphQLDescription("The base fees for the shipment method.")
    val baseFees: Int,
    @property:GraphQLDescription("The fees per item for the shipment method.")
    val feesPerItem: Int,
    @property:GraphQLDescription("The fees per kg for the shipment method.")
    val feesPerKg: Int,
    @property:GraphQLDescription("If this shipment method is archived, the datetime it was archived.")
    val archivedAt: OffsetDateTime?, private val ref: String
) : Node(id) {

    @GraphQLDescription("If true, this shipment method is archived and can no longer be used.")
    val isArchived: Boolean
        get() = archivedAt != null


    @GraphQLDescription("The reference of the shipment method used by the external shipment provider.")
    fun ref(dfe: DataFetchingEnvironment): String {
        dfe.authorizedUser.checkIsEmployee()
        return ref
    }

    @GraphQLDescription("Calculates the fees for a potential shipment.")
    suspend fun calculateFees(
        @GraphQLDescription("The input for the calculation.")
        items: List<CalculateFeesItemInput>,
        @GraphQLIgnore
        @Autowired
        productVariantVersionRepository: ProductVariantVersionRepository
    ): Int {
        val productVariantVersions =
            productVariantVersionRepository.findAllById(items.map { it.productVariantVersionId }).collectList()
                .awaitSingle().associateBy { it.id }
        val weight = items.sumOf { productVariantVersions.getValue(it.productVariantVersionId).weight * it.count }
        val itemCount = items.sumOf { it.count }
        return baseFees + (itemCount* feesPerItem) + (weight * feesPerKg).toInt()
    }

}