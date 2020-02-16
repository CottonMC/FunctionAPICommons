package io.github.cottonmc.functionapi.content.items.commands

import com.mojang.brigadier.CommandDispatcher
import io.github.cottonmc.functionapi.api.content.CommandRegistrator
import io.github.cottonmc.functionapi.content.items.template.ItemMaterialTemplate
import io.github.cottonmc.functionapi.util.DataClassToCommand

class ItemMaterialCommand : CommandRegistrator {
    override fun register(commandDispatcher: CommandDispatcher<Map<String, Any>>) {
        val itemMaterialTemplate = ItemMaterialTemplate()
        DataClassToCommand.registerBackedCommand(itemMaterialTemplate,commandDispatcher)
    }
}