package org.misarch.shipment.event

import io.dapr.Topic
import io.dapr.client.domain.CloudEvent
import org.misarch.shipment.event.model.ProductVariantVersionDTO
import org.misarch.shipment.event.model.UserAddressDTO
import org.misarch.shipment.event.model.VendorAddressDTO
import org.misarch.shipment.service.AddressService
import org.misarch.shipment.service.ProductVariantVersionService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Controller for dapr events
 *
 * @param addressService the user service
 * @param productVariantVersionService the product variant version service
 */
@Controller
class EventController(
    private val addressService: AddressService,
    private val productVariantVersionService: ProductVariantVersionService
) {

    /**
     * Handles a user address created event
     *
     * @param cloudEvent the cloud event containing the user address created
     */
    @Topic(name = ShipmentEvents.USER_ADDRESS_CREATED, pubsubName = ShipmentEvents.PUBSUB_NAME)
    @PostMapping("/subscription/${ShipmentEvents.USER_ADDRESS_CREATED}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun onUserCreated(
        @RequestBody
        cloudEvent: CloudEvent<UserAddressDTO>
    ) {
        addressService.registerUserAddress(cloudEvent.data)
    }

    /**
     * Handles a vendor address created event
     *
     * @param cloudEvent the cloud event containing the vendor address created
     */
    @Topic(name = ShipmentEvents.VENDOR_ADDRESS_CREATED, pubsubName = ShipmentEvents.PUBSUB_NAME)
    @PostMapping("/subscription/${ShipmentEvents.VENDOR_ADDRESS_CREATED}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun onVendorCreated(
        @RequestBody
        cloudEvent: CloudEvent<VendorAddressDTO>
    ) {
        addressService.registerVendorAddress(cloudEvent.data)
    }

    /**
     * Handles a product variant version created event
     *
     * @param cloudEvent the cloud event containing the product variant version created
     */
    @Topic(name = ShipmentEvents.PRODUCT_VARIANT_VERSION_CREATED, pubsubName = ShipmentEvents.PUBSUB_NAME)
    @PostMapping("/subscription/${ShipmentEvents.PRODUCT_VARIANT_VERSION_CREATED}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun onProductVariantVersionCreated(
        @RequestBody
        cloudEvent: CloudEvent<ProductVariantVersionDTO>
    ) {
        productVariantVersionService.registerProductVariantVersion(cloudEvent.data)
    }

}