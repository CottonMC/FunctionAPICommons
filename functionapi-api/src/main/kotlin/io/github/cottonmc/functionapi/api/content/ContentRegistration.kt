package io.github.cottonmc.functionapi.api.content

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier

interface ContentRegistration {
    fun setup(context: Map<String, Any>)
    fun register(id:FunctionAPIIdentifier)
}