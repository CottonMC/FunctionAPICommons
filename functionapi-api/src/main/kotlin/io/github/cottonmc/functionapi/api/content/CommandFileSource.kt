package io.github.cottonmc.functionapi.api.content

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier

interface CommandFileSource {
    val commandFiles: Map<FunctionAPIIdentifier, List<String>>
}