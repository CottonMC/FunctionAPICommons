package io.github.cottonmc.functionapi.api.content.registration

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.util.MissingResourceException

interface Include {
    @Throws(MissingResourceException::class)
    fun include( context:Map<String, Any>,item:FunctionAPIIdentifier)
    fun matches(item: FunctionAPIIdentifier):Boolean
}