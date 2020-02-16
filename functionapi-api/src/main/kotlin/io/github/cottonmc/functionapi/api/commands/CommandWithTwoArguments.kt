package io.github.cottonmc.functionapi.api.commands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException

@FunctionalInterface
interface CommandWithTwoArguments<T, A> {
    @Throws(CommandSyntaxException::class)
    fun execute(context: CommandContext<T>?, source: A, target: A): Int
}