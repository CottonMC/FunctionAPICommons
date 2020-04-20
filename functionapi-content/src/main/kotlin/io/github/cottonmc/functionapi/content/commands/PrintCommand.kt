package io.github.cottonmc.functionapi.content.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.github.cottonmc.functionapi.api.content.CommandRegistrator
import java.util.function.Consumer

class PrintCommand(private val printer: Consumer<String> = Consumer { println(it) }) : CommandRegistrator {
    override fun register(commandDispatcher: CommandDispatcher<MutableMap<String, Any>>) {
        commandDispatcher.register(LiteralArgumentBuilder.literal<MutableMap<String, Any>>("print")
                .then(RequiredArgumentBuilder.argument<MutableMap<String, Any>, String>("message", StringArgumentType.string())
                        .executes { context: CommandContext<MutableMap<String, Any>>? ->
                            val message = StringArgumentType.getString(context, "message")
                            printer.accept(message)
                            1
                        }
                ))
    }
}