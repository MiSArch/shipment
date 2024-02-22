package org.misarch.shipment.event

/**
 * Constants for shipment event topics used in the application
 */
object ShipmentEvents {
    /**
     * Topic for shipment creation events (a shipment has been created)
     */
    const val SHIPMENT_CREATED = "shipment/shipment/created"

    /**
     * Topic for shipment state change events (a shipment state has been changed)
     */
    const val SHIPMENT_STATE_CHANGED = "shipment/shipment/state-changed"

    /**
     * Topic for shipment method creation events (a shipment method has been created)
     */
    const val SHIPMENT_METHOD_CREATED = "shipment/shipment-method/created"

    /**
     * Topic for shipment method archive events (a shipment method has been archived)
     */
    const val SHIPMENT_METHOD_ARCHIVED = "shipment/shipment-method/archived"

    /**
     * Topic for user address creation events (a user address has been created)
     */
    const val USER_ADDRESS_CREATED = "address/user-address/created"

    /**
     * Topic for vendor address creation events (a vendor address has been created)
     */
    const val VENDOR_ADDRESS_CREATED = "address/vendor-address/created"

    /**
     * Topic for product variant version creation events (a product variant version has been created)
     */
    const val PRODUCT_VARIANT_VERSION_CREATED = "product/product-variant-version/created"

    /**
     * Topic for discount service order validation successful events
     */
    const val ORDER_VALIDATION_SUCCESSFUL = "discount/order/validation-successful"

    /**
     * Name of the pubsub component
     */
    const val PUBSUB_NAME = "pubsub"
}