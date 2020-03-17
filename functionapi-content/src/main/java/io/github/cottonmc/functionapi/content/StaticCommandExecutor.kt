package io.github.cottonmc.functionapi.content

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.CommandExecutor
import io.github.cottonmc.functionapi.api.content.FileSource
import io.github.cottonmc.functionapi.api.content.CommandRegistrator
import io.github.cottonmc.functionapi.api.content.registration.Include
import io.github.cottonmc.functionapi.content.commands.IncludeCommand
import io.github.cottonmc.functionapi.content.includes.StaticCommandInclude
import io.github.cottonmc.functionapi.util.MissingResourceException
import io.github.cottonmc.functionapi.util.commandbuilder.DataClassToCommand
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Description
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Name
import io.github.cottonmc.functionapi.util.documentation.MarkdownPrinter
import io.github.cottonmc.functionapi.util.getAnnotations
import io.github.cottonmc.functionapi.util.getMethodByName
import java.io.IOException
import java.io.PrintWriter
import java.io.Writer
import java.util.*

class StaticCommandExecutor() : CommandExecutor {
    private val fileSources: MutableList<FileSource>
    override val commandDispatcher = CommandDispatcher<Map<String, Any>>()
    private val commandFiles: MutableMap<FunctionAPIIdentifier,String> = HashMap()
    private var executed = false

    private val registered = LinkedList<Any>()

    //we keep a reference for this command, as we need to give it file sources and other things.
    private val includeCommand = IncludeCommand()

    init {
        //include static command files.
        register(StaticCommandInclude(this, "mccontent"))
        register(includeCommand)
    }

    override fun register(include: Include) {
        includeCommand.addInclude(include)
    }

    override fun register(command: CommandRegistrator) {
        command.register(commandDispatcher)
        registered.add(command)
    }

    override fun register(fileSource: FileSource) {
        fileSources.add(fileSource)
        commandFiles.putAll(fileSource.files)
    }

    fun registerDataObject(obj: Any) {
        DataClassToCommand.registerBackedCommand(obj, commandDispatcher)
        registered.add(obj)
    }

    @Throws(MissingResourceException::class, CommandSyntaxException::class)
    override fun execute(identifier: FunctionAPIIdentifier, source: Map<String, Any>) {
        if (!executed) {
            executed = true
        }
        val strings = commandFiles[identifier] ?: throw MissingResourceException(identifier)
        for (string in strings.split("\n")) {
            if (string.isBlank() || string.startsWith("#"))
                continue
            println(string)
            commandDispatcher.execute(string, source)
        }
    }

    fun execute(command: String, source: Map<String, Any>) {
        commandDispatcher.execute(command, source)

    }

    override val iDs: Set<FunctionAPIIdentifier>
        get() = commandFiles.keys.toSet()

    init {
        fileSources = ArrayList()
    }

    fun printDocs( name: String) {
        val fileWriter = PrintWriter("./prefabmod-docs.md")
        printDocs(fileWriter)
    }

    fun printDocs( writer: Writer) {
        val titled = LinkedList<String>()
        val titledMethods = LinkedList<String>()
        val md = MarkdownPrinter(writer)

        val contentRegistrationContext: Map<String, Any> = HashMap()
        val root = commandDispatcher.root
        val allUsage = commandDispatcher.getAllUsage(root, contentRegistrationContext, false)
        try {
            writer.write("Prefab mod content registration command reference:" + System.lineSeparator())
            for (s in allUsage) {
                val commandParts = s.split(" ")
                val command = commandParts[0]

                for (any in registered) {
                    getAnnotations(any::class.java, Name::class.java).ifPresent {
                        if (it.name == command) {
                            if(!titled.contains(command)) {
                                titled.add(command)
                                getAnnotations(any::class.java, Description::class.java).ifPresent { docs ->
                                    md.header1("$command:")
                                    md.paragraph(docs.description)
                                }
                            }
                            if(!titledMethods.contains("${commandParts[0]} ${commandParts[1]}")) {
                                getMethodByName(any,commandParts[1]).ifPresent {method->
                                    getAnnotations(method, Description::class.java).ifPresent { docs ->
                                        md.paragraph(docs.description)
                                    }
                                }
                                titledMethods.add("${commandParts[0]} ${commandParts[1]}")
                            }
                        }
                    }
                }
                md.br().code(s).br()
            }
            writer.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}