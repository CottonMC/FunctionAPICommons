package io.github.cottonmc.functionapi.api.script

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier

/**
 * Contains information that is required for scripting.
 */
interface ScriptedObject {
    /**
     * Get the functionAPIIdentifier of this object (eg: minecraft:dirt)
     */
    val eventID: FunctionAPIIdentifier?

    /**
     * Get the type of this object, eg "block".
     */
    val eventType: String?
}