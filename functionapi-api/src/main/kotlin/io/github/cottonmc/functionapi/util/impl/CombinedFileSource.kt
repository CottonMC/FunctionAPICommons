package io.github.cottonmc.functionapi.util.impl

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.FileSource
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

class CombinedFileSource(private val fileSources: Array<FileSource>) : FileSource {

    override val files: Map<FunctionAPIIdentifier, String>
        get() {

            val result = HashMap<FunctionAPIIdentifier, String>()

            for (fileSource in fileSources) {
                result.putAll(fileSource.files)
            }

            return Collections.unmodifiableMap(result)
        }

}