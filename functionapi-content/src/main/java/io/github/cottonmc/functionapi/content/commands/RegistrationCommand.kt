package io.github.cottonmc.functionapi.content.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.CommandRegistrator
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType
import io.github.cottonmc.functionapi.content.template.RegistrationTemplate
import io.github.cottonmc.functionapi.util.DataClassToCommand
import io.github.cottonmc.functionapi.util.PermanentHashmap
import java.util.*

class RegistrationCommand : CommandRegistrator {

    override fun register(commandDispatcher: CommandDispatcher<Map<String, Any>>) {
        val registrationTemplate = RegistrationTemplate()
        DataClassToCommand.registerBackedCommand(registrationTemplate, commandDispatcher)
    }
}