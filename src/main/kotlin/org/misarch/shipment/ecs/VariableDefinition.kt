package org.misarch.shipment.ecs

/**
 * Definition of an experiment configuration variable
 *
 * @property type the type of the variable
 * @property defaultValue the default value of the variable
 */
data class VariableDefinition(
    val type: Any,
    val defaultValue : Any,
)