package org.misarch.shipment.provider

import org.misarch.shipment.graphql.model.ShipmentStatus
import org.misarch.shipment.service.ShipmentService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

/**
 * Controller for the external shipment provider
 * Receives shipment status updates from the provider
 *
 * @property shipmentService service for handling shipments
 */
@Controller
class ShipmentProviderController(
    private val shipmentService: ShipmentService
) {

    @PostMapping("/shipment/{id}/status")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun updateShipmentStatus(
        @PathVariable("id")
        id: UUID,
        @RequestBody
        input: UpdateStatusInput
    ) {
        shipmentService.updateShipmentStatus(id, input.status)
    }

}