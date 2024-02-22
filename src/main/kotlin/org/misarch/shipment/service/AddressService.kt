package org.misarch.shipment.service

import org.misarch.shipment.event.model.UserAddressDTO
import org.misarch.shipment.event.model.VendorAddressDTO
import org.misarch.shipment.persistence.model.AddressEntity
import org.misarch.shipment.persistence.repository.AddressRepository
import org.springframework.stereotype.Service

/**
 * Service for [AddressEntity]s
 *
 * @param repository the repository for [AddressEntity]s
 */
@Service
class AddressService(
    repository: AddressRepository
) : BaseService<AddressEntity, AddressRepository>(repository) {

    /**
     * Registers a user address
     *
     * @param userAddressDTO the user to register
     */
    suspend fun registerUserAddress(userAddressDTO: UserAddressDTO) {
        repository.createAddress(userAddressDTO.id, userAddressDTO.userId)
    }

    /**
     * Registers a vendor address
     *
     * @param vendorAddressDTO the vendor to register
     */
    suspend fun registerVendorAddress(vendorAddressDTO: VendorAddressDTO) {
        repository.createAddress(vendorAddressDTO.id, null)
    }

}