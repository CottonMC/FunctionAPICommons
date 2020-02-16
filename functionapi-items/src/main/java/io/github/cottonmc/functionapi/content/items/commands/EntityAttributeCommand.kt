package io.github.cottonmc.functionapi.content.items.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.cottonmc.functionapi.api.content.CommandRegistrator

class EntityAttributeCommand : CommandRegistrator {
    override fun register(commandDispatcher: CommandDispatcher<Map<String, Any>>) {
        val addentityAttribute = LiteralArgumentBuilder.literal<Map<String, Any>>("entityattribute")
    }
}