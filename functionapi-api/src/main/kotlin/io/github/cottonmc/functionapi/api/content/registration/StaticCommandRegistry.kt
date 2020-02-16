package io.github.cottonmc.functionapi.api.content.registration

import com.mojang.brigadier.CommandDispatcher

/**
 * Used as a service to register commands into the common executor.
 */
interface StaticCommandRegistry {
    fun register(dispatcher: CommandDispatcher<Map<String?, Any?>?>?)
}