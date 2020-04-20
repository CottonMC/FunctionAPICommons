package io.github.cottonmc.functionapi.api.content

import com.mojang.brigadier.CommandDispatcher

interface CommandRegistrator {
    fun register(commandDispatcher: CommandDispatcher<MutableMap<String, Any>>)
}