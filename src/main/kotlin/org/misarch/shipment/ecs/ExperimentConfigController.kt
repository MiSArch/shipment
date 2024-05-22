package org.misarch.shipment.ecs

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Handles requests from the experiment configuration sidecar
 *
 * @param experimentConfigService the experiment configuration service
 */
@RestController
class ExperimentConfigController(
    private val experimentConfigService: ExperimentConfigService
) {

    /**
     * Returns the defined variables for the experiment configuration
     */
    @GetMapping("/ecs/defined-variables")
    fun getDefinedVariables(): Map<String, VariableDefinition> {
        return mapOf(
            ExperimentConfigService.PROVIDER_RETRIES_NAME to ExperimentConfigService.PROVIDER_RETRIES_DEFINITION,
            ExperimentConfigService.PROVIDER_RETRY_DELAY_NAME to ExperimentConfigService.PROVIDER_RETRY_DELAY_DEFINITION
        )
    }

    /**
     * Sets the variables for the experiment configuration
     *
     * @param variables the variables to set
     */
    @PostMapping("/ecs/variables")
    fun setVariables(
        @RequestBody
        variables: Map<String, Any>
    ) {
        variables.forEach { (name, value) ->
            when (name) {
                ExperimentConfigService.PROVIDER_RETRIES_NAME -> experimentConfigService.providerRetries = value as Int
                ExperimentConfigService.PROVIDER_RETRY_DELAY_NAME -> experimentConfigService.providerRetryDelay =
                    value as Int

                else -> error("Unknown variable: $name")
            }
        }
    }

}