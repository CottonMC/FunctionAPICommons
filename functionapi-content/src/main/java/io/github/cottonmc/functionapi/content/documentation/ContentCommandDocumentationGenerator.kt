package io.github.cottonmc.functionapi.content.documentation

import com.mojang.brigadier.CommandDispatcher
import java.io.IOException
import java.io.PrintWriter
import java.io.Writer
import java.util.*

class ContentCommandDocumentationGenerator {

    fun generate(dispatcher: CommandDispatcher<Map<String, Any>>,name:String){
        val fileWriter = PrintWriter("./functionapi/$name.txt")
        generate(dispatcher,fileWriter)
    }

    fun generate(dispatcher: CommandDispatcher<Map<String, Any>>,writer: Writer) {
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