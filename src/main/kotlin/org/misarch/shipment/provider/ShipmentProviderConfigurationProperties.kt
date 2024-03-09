package org.misarch.shipment.provider

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("misarch.shipment.provider")
data class ShipmentProviderConfigurationProperties(val endpoint: String)