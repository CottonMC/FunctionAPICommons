package io.github.cottonmc.functionapi.script.commandtemplates;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;


public abstract class BlockStateCommandTemplate<T, BP> {

    protected abstract ArgumentType<BP> blockPosArgumentType();

    protected abstract int handle(CommandContext<T> context, String name, String value);


    protected abstract boolean permission(T source);


    public void register(CommandDispatcher<T> commandDispatcher_1) {
        commandDispatcher_1.register(
                LiteralArgumentBuilder.<T>literal("setstate")
                        .requires(this::permission)
                        .then(RequiredArgumentBuilder.<T, BP>argument("target", blockPosArgumentType())
                                .then(RequiredArgumentBuilder.<T, String>argument("name", StringArgumentType.string())
                                        .then(RequiredArgumentBuilder.<T, String>argument("value", StringArgumentType.string())
                                                .executes(context -> {
                                                    String name = StringArgumentType.getString(context, "name");
                                                    String value = StringArgumentType.getString(context, "value");
                                                    return handle(context, name, value);
                                                })
                                        )
                                )));
    }


}
