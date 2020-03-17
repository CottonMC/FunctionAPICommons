package io.github.cottonmc.functionapi.util

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.util.impl.FunctionAPIIdentifierImpl
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class FileTemplateHelper<T>(private val templateCreator: TemplateFunction<T>, private val rootFolder: List<File>, private val extension: String) {
    val templates: Map<FunctionAPIIdentifier, T>
        get() {
            val result: MutableMap<FunctionAPIIdentifier, T> = HashMap()
            for (namespaceRoot in rootFolder) {
                val namespace = namespaceRoot.parentFile.name
                val files = namespaceRoot.list()
                if (files != null) {
                    for (childFile in files) {
                        if (!childFile.endsWith(extension)) {
                            continue
                        }
                        val templateFile = File(namespaceRoot.absolutePath + "/" + childFile)
                        val path = templateFile
                                .absolutePath
                                .replace("\\", "/")
                                .replace(namespaceRoot.absolutePath.replace("\\", "/"), "")
                                .replace(".$extension", "")
                                .replaceFirst("^/\\w*/".toRegex(), "")
                                .replace("^/", "")
                        try {
                            val s = path.replace("^/".toRegex(), "")
                            if (!s.isEmpty()) result[FunctionAPIIdentifierImpl(namespace.replace(":".toRegex(), ""), s)] = templateCreator[templateFile]
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            return result
        }

    interface TemplateFunction<T> {
        @Throws(FileNotFoundException::class)
        operator fun get(source: File?): T
    }

}