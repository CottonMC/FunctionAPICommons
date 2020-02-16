package io.github.cottonmc.functionapi.content.items.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.github.cottonmc.functionapi.api.content.CommandRegistrator
import io.github.cottonmc.functionapi.api.content.ItemTemplate
import io.github.cottonmc.functionapi.api.content.enums.ItemType
import io.github.cottonmc.functionapi.content.items.template.ItemTemplateImpl
import io.github.cottonmc.functionapi.util.DataClassToCommand

class ItemSettingsCommand : CommandRegistrator {
    override fun register(commandDispatcher: CommandDispatcher<Map<String, Any>>) {
        DataClassToCommand.registerBackedCommand(ItemTemplateImpl(),commandDispatcher)
    }
}