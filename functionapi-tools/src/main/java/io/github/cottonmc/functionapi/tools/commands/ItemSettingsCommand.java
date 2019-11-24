package io.github.cottonmc.functionapi.tools.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.github.cottonmc.functionapi.api.content.ItemTemplate;
import io.github.cottonmc.functionapi.api.content.enums.ItemType;
import io.github.cottonmc.functionapi.tools.template.ItemTemplateImpl;

import java.util.Map;

public class ItemSettingsCommand {


    private static ItemTemplate getSource(CommandContext<Map<String, Object>> context){
        return (ItemTemplate) context.getSource().getOrDefault("item",new ItemTemplateImpl());
    }


    public static void register(CommandDispatcher<Map<String, Object>> commandDispatcher) {
        commandDispatcher.register(LiteralArgumentBuilder.<Map<String, Object>>literal("itemsettings")
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("stacksize")
                        .then(RequiredArgumentBuilder.<Map<String, Object>, Integer>argument("amount", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                    getSource(context).setMaxCount(amount);
                                    return 1;
                                })))

                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("loottableid")
                        .then(RequiredArgumentBuilder.<Map<String, Object>, String>argument("id", StringArgumentType.string())
                                .executes(context -> {
                                    String id = StringArgumentType.getString(context, "id");
                                    getSource(context).setLootId(id);
                                    return 1;
                                })))
                .then(typeNode()));

    }

    private static LiteralArgumentBuilder<Map<String, Object>> typeNode() {
        LiteralArgumentBuilder<Map<String, Object>> type = LiteralArgumentBuilder.literal("type");

        for (ItemType value : ItemType.values()) {
            type.then(LiteralArgumentBuilder.<Map<String, Object>>literal(value.name().toLowerCase())
                    .executes(context -> {
                         getSource(context).setType(value);
                         return 1;
                    }));
        }

        return type;

    }
}
