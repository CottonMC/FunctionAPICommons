package io.github.cottonmc.functionapi.api.content

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.ParseResults
import com.mojang.brigadier.exceptions.CommandSyntaxException
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.registration.Include
import java.util.*

interface CommandExecutor {
    fun register(command: CommandRegistrator)
    fun register(include: Include)
    fun register(commandFileSource: CommandFileSource)
    val commandDispatcher: CommandDispatcher<Map<String, Any>>
    @Throws(MissingResourceException::class, CommandSyntaxException::class)
    fun execute(identifier: FunctionAPIIdentifier, source: Map<String, Any>)

    val iDs: Set<FunctionAPIIdentifier>
}