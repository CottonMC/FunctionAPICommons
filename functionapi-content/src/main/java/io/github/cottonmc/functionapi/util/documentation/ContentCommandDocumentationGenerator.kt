package io.github.cottonmc.functionapi.util.documentation

import com.mojang.brigadier.CommandDispatcher
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.io.Writer
import java.util.*

class ContentCommandDocumentationGenerator(private val writer: Writer) {

    constructor(name: String) : this(PrintWriter(
            ({
                File("./functionapi/").mkdirs()
                "./functionapi/$name.md"
            })()))


    fun generate(dispatcher: CommandDispatcher<Map<String, Any>>) {
        val contentRegistrationContext: Map<String, Any> = HashMap()
        val root = dispatcher.root
        val allUsage = dispatcher.getAllUsage(root, contentRegistrationContext, false)
        try {
            writer.write("Function api registration command reference: " + System.lineSeparator())
            for (s in allUsage) {
                writer.append(s).append(System.lineSeparator())
            }
            writer.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}