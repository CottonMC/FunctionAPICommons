package io.github.cottonmc.functionapi.content

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.CommandExecutor
import io.github.cottonmc.functionapi.api.content.CommandFileSource
import io.github.cottonmc.functionapi.api.content.CommandRegistrator
import io.github.cottonmc.functionapi.api.content.registration.Include
import io.github.cottonmc.functionapi.content.commands.IncludeCommand
import io.github.cottonmc.functionapi.content.commands.PrintCommand
import io.github.cottonmc.functionapi.content.commands.RegistrationCommand
import io.github.cottonmc.functionapi.content.documentation.ContentCommandDocumentationGenerator
import io.github.cottonmc.functionapi.content.includes.StaticCommandInclude
import io.github.cottonmc.functionapi.util.MissingResourceException
import java.util.*

class StaticCommandExecutor() : CommandExecutor {
    private val commandFileSources: MutableList<CommandFileSource>
    override val commandDispatcher = CommandDispatcher<Map<String, Any>>()
    private val commandFiles: MutableMap<FunctionAPIIdentifier, List<String>> = HashMap()
    private var executed = false

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
    }

    override fun register(commandFileSource: CommandFileSource) {
        commandFileSources.add(commandFileSource)
        commandFiles.putAll(commandFileSource.commandFiles)
    }

    @Throws(MissingResourceException::class, CommandSyntaxException::class)
    override fun execute(identifier: FunctionAPIIdentifier, source: Map<String, Any>) {
        if (!executed) {
            executed = true
        }
        val strings = commandFiles[identifier] ?: throw MissingResourceException(identifier!!)
        for (string in strings) {
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
        commandFileSources = ArrayList()
    }
}