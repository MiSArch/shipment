package org.misarch.shipment.ecs

import org.springframework.stereotype.Service

/**
 * Service for handling experiment configuration variables
 */
@Service
class ExperimentConfigService {

    companion object {
        /**
         * The name of the provider retries variable
         */
        const val PROVIDER_RETRIES_NAME = "providerRetries"

        /**
         * The definition of the provider retries variable
         */
        val PROVIDER_RETRIES_DEFINITION = VariableDefinition(object {
            val `$schema` = "http://json-schema.org/draft-07/schema#"
            val type = "integer"
            val minimum = 0
        }, 3)

        /**
         * The name of the provider retry delay variable
         */
        const val PROVIDER_RETRY_DELAY_NAME = "providerRetryDelay"

        /**
         * The definition of the provider retry delay variable
         */
        val PROVIDER_RETRY_DELAY_DEFINITION = VariableDefinition(object {
            val `$schema` = "http://json-schema.org/draft-07/schema#"
            val type = "integer"
            val minimum = 0
        }, 500)
    }

    /**
     * The number of retries for the provider
     * Does NOT include the initial request
     */
    var providerRetries: Int = 3

    /**
     * The delay between retries for the provider
     */
    var providerRetryDelay: Int = 500

}