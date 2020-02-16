package io.github.cottonmc.functionapi.api.commands

import com.mojang.brigadier.context.CommandContext

@FunctionalInterface
interface CommandWithArgument<T, A> {
    fun execute(context: CommandContext<T>?, argument: A): Int
}