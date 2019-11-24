package io.github.cottonmc.functionapi.blocks.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.api.content.ContentRegistrationContext;

import java.util.Map;

public class BlockCollosionCommand {

    public static void register(CommandDispatcher<Map<String,Object>> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<Map<String,Object>>literal("blockcollosion")
                .then(RequiredArgumentBuilder.<Map<String,Object>, String>argument("statename", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.<Map<String,Object>, String>argument("statename", StringArgumentType.string())
                                .then(RequiredArgumentBuilder.<Map<String,Object>, Float>argument("x1", FloatArgumentType.floatArg(0, 16))
                                        .then(RequiredArgumentBuilder.<Map<String,Object>, Float>argument("y1", FloatArgumentType.floatArg(0, 16))
                                                .then(RequiredArgumentBuilder.<Map<String,Object>, Float>argument("z1", FloatArgumentType.floatArg(0, 16))
                                                        .then(RequiredArgumentBuilder.<Map<String,Object>, Float>argument("x2", FloatArgumentType.floatArg(0, 16))
                                                                .then(RequiredArgumentBuilder.<Map<String,Object>, Float>argument("y2", FloatArgumentType.floatArg(0, 16))
                                                                        .then(RequiredArgumentBuilder.<Map<String,Object>, Float>argument("z2", FloatArgumentType.floatArg(0, 16))
                                                                                .executes(context -> {

                                                                                    return 1;
                                                                                })
                                                                        )))))))));
    }
}
