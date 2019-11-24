package io.github.cottonmc.functionapi.tools.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType;

import java.util.*;

@SuppressWarnings("unchecked")
public class RegistrationCommand {

    public static void register(CommandDispatcher<Map<String, Object>> commandDispatcher) {

        commandDispatcher.register(LiteralArgumentBuilder.<Map<String, Object>>literal("registeritem")
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("addpostfix")
                        .then(RequiredArgumentBuilder.<Map<String, Object>, String>argument("postfix", StringArgumentType.word())
                                .executes(context -> {
                                    String postfix = StringArgumentType.getString(context, "postfix");
                                    ((List<String>)context.getSource().getOrDefault("postfix",new ArrayList<String>())).add(postfix);
                                    return 1;
                                })
                        ))
                .then(RequiredArgumentBuilder.<Map<String, Object>, FunctionAPIIdentifier>argument("id", FunctionAPIIdentifierArgumentType.identifier())
                        .executes(context -> {
                            FunctionAPIIdentifier functionAPIIdentifier = context.getArgument("id", FunctionAPIIdentifier.class);
                            ((List<FunctionAPIIdentifier>)context.getSource().getOrDefault("postfix",new ArrayList<FunctionAPIIdentifier>())).add(functionAPIIdentifier);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("variant")
                        .then(RequiredArgumentBuilder.<Map<String, Object>, Integer>argument("variant_index", IntegerArgumentType.integer())
                                .then(RequiredArgumentBuilder.<Map<String, Object>, String>argument("variant_name", StringArgumentType.word())
                                        .executes(context -> {
                                            String postfix = StringArgumentType.getString(context, "variant_name");
                                            int variant_index = IntegerArgumentType.getInteger(context, "variant_index");
                                            ((Map<Integer,List<String>>)context.getSource().getOrDefault("variant",new HashMap<Integer,List<String>>())).getOrDefault(variant_index,new LinkedList<>()).add(postfix);
                                            return 1;
                                        }))
                        )))
        ;
    }}
