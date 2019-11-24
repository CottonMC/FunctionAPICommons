package io.github.cottonmc.functionapi.tools.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import java.util.Map;

public class EntityAttributeCommand {

    public static void register(CommandDispatcher<Map<String, Object>> commandDispatcher) {

        LiteralArgumentBuilder<Map<String, Object>> addentityAttribute = LiteralArgumentBuilder.literal("entityattribute");



    }

}
