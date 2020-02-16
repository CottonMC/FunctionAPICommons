package io.github.cottonmc.functionapi.util

import java.io.File
import java.util.*

class DirectoryManager private constructor() {
    private val rootFolder = "io/github/cottonmc/functionapi"
    private val itemFolder = "/item"
    private val blockColorFolder = "/color/block"
    private val itemColorFolder = "/color/item"
    private val toolMaterialFolder = "/tool_material"
    private val armorMaterialFolder = "/armor_material"
    val contentFolder: List<File>
        get() {
            val contentFolder = "/content"
            return getFolders(contentFolder)
        }

    private fun getFolders(targetFolder: String): List<File> {
        createRoot()
        val file = File(rootFolder)
        val blockFolders: MutableList<File> = LinkedList()
        if (file.exists()) {
            val namespaces = file.listFiles()
            if (namespaces != null) for (namespace in namespaces) {
                val namespaceAbsolutePath = namespace.absolutePath
                blockFolders.add(File(namespaceAbsolutePath + targetFolder))
            }
        }
        return blockFolders
    }

    private fun createRoot() {
        val file = File(rootFolder)
        file.mkdirs()
        if (!file.exists()) {
            throw DirectoryStateException(file)
        }
    }

    internal inner class DirectoryStateException(directory: File) : RuntimeException("Could not create directory: " + directory.absolutePath)
    companion object {
        val iNSTANCE = DirectoryManager()
    }
}