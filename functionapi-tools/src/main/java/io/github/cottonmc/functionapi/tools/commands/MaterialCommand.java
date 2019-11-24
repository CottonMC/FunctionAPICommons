package io.github.cottonmc.functionapi.tools.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.functionapi.api.content.ItemMaterial;
import io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot;
import io.github.cottonmc.functionapi.tools.template.ItemMaterialImpl;

import java.util.Map;

public class MaterialCommand {

    private static ItemMaterial getSource(CommandContext<Map<String, Object>> context){
        return (ItemMaterial) context.getSource().getOrDefault("item_material",new ItemMaterialImpl());
    }

    public static void register(CommandDispatcher<Map<String, Object>> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<Map<String, Object>>literal("material")
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("durability")
                                .then(RequiredArgumentBuilder.<Map<String, Object>, Integer>argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            getSource(context).setDurability(EquipmentSlot.CHESTPLATE,IntegerArgumentType.getInteger(context,"amount"));
                                            return 1;
                                        })
                                )
                        )
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("enchantability")
                                .then(RequiredArgumentBuilder.<Map<String, Object>, Integer>argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            getSource(context).setEnchantability(IntegerArgumentType.getInteger(context,"amount"));
                                            return 1;
                                        })
                                )
                        )
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("protection")
                                .then(RequiredArgumentBuilder.<Map<String, Object>, Integer>argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            getSource(context).setProtectionAmount(EquipmentSlot.CHESTPLATE,IntegerArgumentType.getInteger(context,"amount"));
                                            return 1;
                                        })
                                )
                        )
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("mininglevel")
                                .then(RequiredArgumentBuilder.<Map<String, Object>, Integer>argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            getSource(context).setMiningLevel(IntegerArgumentType.getInteger(context,"amount"));
                                            return 1;
                                        })
                                )
                        )
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("toughness")
                                .then(RequiredArgumentBuilder.<Map<String, Object>, Float>argument("amount", FloatArgumentType.floatArg())
                                        .executes(context -> {
                                            getSource(context).setToughness(IntegerArgumentType.getInteger(context,"amount"));
                                            return 1;
                                        })
                                )
                        )
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("miningspeed")
                                .then(RequiredArgumentBuilder.<Map<String, Object>, Float>argument("amount", FloatArgumentType.floatArg())
                                        .executes(context -> {
                                            getSource(context).setMiningSpeed(IntegerArgumentType.getInteger(context,"amount"));
                                            return 1;
                                        })
                                )
                        )
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("attackdamage")
                                .then(RequiredArgumentBuilder.<Map<String, Object>, Float>argument("amount", FloatArgumentType.floatArg())
                                        .executes(context -> {
                                            getSource(context).setAttackDamage(IntegerArgumentType.getInteger(context,"amount"));
                                            return 1;
                                        })
                                )
                        )
        );
    }
}
