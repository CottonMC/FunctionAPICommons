package io.github.cottonmc.functionapi.api.content

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier

interface FileSource {
    val files: Map<FunctionAPIIdentifier, String>
}