package org.misarch.shipment.graphql.dataloader

import org.misarch.shipment.graphql.model.Address
import org.misarch.shipment.persistence.model.AddressEntity
import org.misarch.shipment.persistence.repository.AddressRepository
import org.springframework.stereotype.Component

/**
 * Data loader for [Address]es
 *
 * @param repository repository for [AddressEntity]s
 */
@Component
class AddressDataLoader(
    repository: AddressRepository
) : IdDataLoader<Address, AddressEntity>(repository)