package org.misarch.shipment

import com.infobip.spring.data.r2dbc.EnableQuerydslR2dbcRepositories
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableQuerydslR2dbcRepositories
@ConfigurationPropertiesScan
class ShipmentApplication

fun main(args: Array<String>) {
    runApplication<ShipmentApplication>(*args)
}