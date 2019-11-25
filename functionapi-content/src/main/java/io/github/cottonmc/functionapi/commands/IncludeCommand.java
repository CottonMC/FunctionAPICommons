package io.github.cottonmc.functionapi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.StaticCommandExecutor;
import io.github.cottonmc.functionapi.api.IncludeCommandRunner;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType;
import io.github.cottonmc.functionapi.util.MissingResourceException;

import java.util.Map;


public class IncludeCommand {

    public static void register(CommandDispatcher<Map<String,Object>> commandDispatcher) {

        commandDispatcher.register(LiteralArgumentBuilder.<Map<String,Object>>literal("include")
                .then(RequiredArgumentBuilder.<Map<String,Object>, FunctionAPIIdentifier>argument("id", FunctionAPIIdentifierArgumentType.identifier())
                        .executes(context -> {
                            FunctionAPIIdentifier functionAPIIdentifier = context.getArgument("id", FunctionAPIIdentifier.class);
                            try {
                                StaticCommandExecutor.INSTANCE.execute(functionAPIIdentifier,context.getSource());
                            } catch (MissingResourceException e) {
                                e.printStackTrace();
                            }
                            return 1;
                        })
                ));
    }

}
