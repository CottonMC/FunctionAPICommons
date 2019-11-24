package io.github.cottonmc.functionapi.blocks.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.functionapi.api.content.BlockTemplate;
import io.github.cottonmc.functionapi.api.content.enums.BlockRenderLayer;
import io.github.cottonmc.functionapi.api.content.enums.BlockSoundGroup;
import io.github.cottonmc.functionapi.api.content.enums.Material;
import io.github.cottonmc.functionapi.api.content.enums.Tools;
import io.github.cottonmc.functionapi.blocks.template.BlockTemplateImpl;

import java.util.HashMap;
import java.util.Map;

public class BlockSettingsCommand {
    
    
    private static BlockTemplate getSource(CommandContext<Map<String, Object>> context){
        return (BlockTemplate) context.getSource().getOrDefault("block",new BlockTemplateImpl());
    }
    
    public static void register(CommandDispatcher<Map<String, Object>> commandDispatcher) {

        commandDispatcher.register(LiteralArgumentBuilder.<Map<String, Object>>literal("blocksettings")
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("mininglevel")
                        .then(RequiredArgumentBuilder.<Map<String, Object>, Integer>argument("level", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int level = IntegerArgumentType.getInteger(context, "level");
                                    getSource(context).setMiningLevel(level);
                                    return 1;
                                }))
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("waterlogged")
                        .executes(context -> {
                            getSource(context).makeWaterlogged();
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("hardness")
                        .then(RequiredArgumentBuilder.<Map<String, Object>, Integer>argument("level", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int level = IntegerArgumentType.getInteger(context, "level");
                                    getSource(context).setHardness(level);
                                    return 1;
                                }))
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("disableitem")
                        .executes(context -> {
                            getSource(context).setHasItem(false);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("ticksrandomly")
                        .executes(context -> {
                            getSource(context).setTicksRandomly(true);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("invisible")
                        .executes(context -> {
                            getSource(context).setInvisible(true);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("ethereal")
                        .executes(context -> {
                            getSource(context).setCollidable(false);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("air")
                        .executes(context -> {
                            getSource(context).setAir(true);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("nomobspawning")
                        .executes(context -> {
                            getSource(context).setCanSpawnMobs(false);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("makestairs")
                        .executes(context -> {
                            getSource(context).makeStairs();
                            return 1;
                        }))
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("rendermode")
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("translucent")
                                .executes(context -> {
                                    getSource(context).setRenderLayer(BlockRenderLayer.TRANSLUCENT);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("cutout")
                                .executes(context -> {
                                    getSource(context).setRenderLayer(BlockRenderLayer.CUTOUT);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("cutoutmipped")
                                .executes(context -> {
                                    getSource(context).setRenderLayer(BlockRenderLayer.CUTOUT_MIPPED);
                                    return 1;
                                }))
                )
                .then(LiteralArgumentBuilder.<Map<String, Object>>literal("tool")
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("pickaxe")
                                .executes(context -> {
                                    getSource(context).setTool(Tools.PICKAXES);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("axe")
                                .executes(context -> {
                                    getSource(context).setTool(Tools.AXES);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("shovel")
                                .executes(context -> {
                                    getSource(context).setTool(Tools.SHOVELS);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("hoe")
                                .executes(context -> {
                                    getSource(context).setTool(Tools.HOES);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("sword")
                                .executes(context -> {
                                    getSource(context).setTool(Tools.SWORDS);
                                    return 1;
                                }))

                ).then(LiteralArgumentBuilder.<Map<String, Object>>literal("state")
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("range")
                                .then(RequiredArgumentBuilder.<Map<String, Object>, String>argument("name", StringArgumentType.string())
                                        .then(RequiredArgumentBuilder.<Map<String, Object>, Integer>argument("max", IntegerArgumentType.integer())
                                                .executes(context -> {

                                                    String name = StringArgumentType.getString(context, "name");
                                                    if (isNameIllegal(name))
                                                        return 0;
                                                    int max = IntegerArgumentType.getInteger(context, "max");

                                                    getSource(context).createIntProperty(name, max);
                                                    return 1;
                                                })
                                        )
                                ))
                        .then(LiteralArgumentBuilder.<Map<String, Object>>literal("boolean")
                                .then(RequiredArgumentBuilder.<Map<String, Object>, String>argument("name", StringArgumentType.string())
                                        .executes(context -> {
                                            String name = StringArgumentType.getString(context, "name");
                                            if (isNameIllegal(name))
                                                return 0;
                                            getSource(context).createBooleanProperty(name);
                                            return 1;
                                        })))
                        .then(RequiredArgumentBuilder.<Map<String, Object>, String>argument("name", StringArgumentType.string())
                                .then(RequiredArgumentBuilder.<Map<String, Object>, String>argument("value", StringArgumentType.string())
                                        .executes(context -> {
                                            String name = StringArgumentType.getString(context, "name");
                                            if (isNameIllegal(name))
                                                return 0;
                                            String value = StringArgumentType.getString(context, "value");
                                            getSource(context).addToStringProperty(name, value);
                                            return 1;
                                        })
                                )))
                .then(materialNode())
                .then(soundNode())
        );
    }

    private static LiteralArgumentBuilder<Map<String, Object>> materialNode() {
        LiteralArgumentBuilder<Map<String, Object>> materialArgument = LiteralArgumentBuilder.literal("material");
        HashMap<Material, String> materialHashMap = new HashMap<>();

        materialHashMap.put(Material.AIR, "air");
        materialHashMap.put(Material.ANVIL, "anvil");
        materialHashMap.put(Material.BAMBOO, "bamboo");
        materialHashMap.put(Material.BAMBOO_SAPLING, "bamboo_sapling");
        materialHashMap.put(Material.BARRIER, "barrier");
        materialHashMap.put(Material.BUBBLE_COLUMN, "bubble_column");
        materialHashMap.put(Material.CACTUS, "cactus");
        materialHashMap.put(Material.CAKE, "cake");
        materialHashMap.put(Material.CARPET, "carpet");
        materialHashMap.put(Material.CLAY, "clay");
        materialHashMap.put(Material.COBWEB, "cobweb");
        materialHashMap.put(Material.EARTH, "earth");
        materialHashMap.put(Material.EGG, "egg");
        materialHashMap.put(Material.FIRE, "fire");
        materialHashMap.put(Material.GLASS, "glass");
        materialHashMap.put(Material.ICE, "ice");
        materialHashMap.put(Material.LAVA, "lava");
        materialHashMap.put(Material.LEAVES, "leaves");
        materialHashMap.put(Material.METAL, "metal");
        materialHashMap.put(Material.ORGANIC, "organic");
        materialHashMap.put(Material.PACKED_ICE, "packed_ice");
        materialHashMap.put(Material.PISTON, "piston");
        materialHashMap.put(Material.PLANT, "plant");
        materialHashMap.put(Material.PORTAL, "portal");
        materialHashMap.put(Material.PUMPKIN, "pumpkin");
        materialHashMap.put(Material.REDSTONE_LAMP, "redstone_lamp");
        materialHashMap.put(Material.REPLACEABLE_PLANT, "replaceable_plant");
        materialHashMap.put(Material.SAND, "sand");
        materialHashMap.put(Material.SEAGRASS, "seagrass");
        materialHashMap.put(Material.SHULKER_BOX, "shulker_box");
        materialHashMap.put(Material.SNOW, "snow");
        materialHashMap.put(Material.SNOW_BLOCK, "snow_block");
        materialHashMap.put(Material.SPONGE, "sponge");
        materialHashMap.put(Material.STONE, "stone");
        materialHashMap.put(Material.STRUCTURE_VOID, "structure_void");
        materialHashMap.put(Material.UNDERWATER_PLANT, "underwater_plant");
        materialHashMap.put(Material.WATER, "water");
        materialHashMap.put(Material.WOOD, "wood");
        materialHashMap.put(Material.WOOL, "wool");

        materialHashMap.forEach((material, s) ->
                materialArgument.then(LiteralArgumentBuilder.<Map<String, Object>>literal(s)
                        .executes(context -> {
                            getSource(context).setMaterial(material);
                            return 1;
                        })
                ));

        return materialArgument;
    }

    private static LiteralArgumentBuilder<Map<String, Object>> soundNode() {
        LiteralArgumentBuilder<Map<String, Object>> materialArgument = LiteralArgumentBuilder.literal("sound");
        HashMap<BlockSoundGroup, String> soundGroupStringHashMap = new HashMap<>();

        soundGroupStringHashMap.put(BlockSoundGroup.ANVIL, "anvil");
        soundGroupStringHashMap.put(BlockSoundGroup.BAMBOO, "bamboo");
        soundGroupStringHashMap.put(BlockSoundGroup.BAMBOO_SAPLING, "bamboo_sapling");
        soundGroupStringHashMap.put(BlockSoundGroup.CORAL, "coral");
        soundGroupStringHashMap.put(BlockSoundGroup.CROP, "crop");
        soundGroupStringHashMap.put(BlockSoundGroup.GLASS, "glass");
        soundGroupStringHashMap.put(BlockSoundGroup.GRASS, "grass");
        soundGroupStringHashMap.put(BlockSoundGroup.GRAVEL, "gravel");
        soundGroupStringHashMap.put(BlockSoundGroup.LADDER, "ladder");
        soundGroupStringHashMap.put(BlockSoundGroup.LANTERN, "lantern");
        soundGroupStringHashMap.put(BlockSoundGroup.METAL, "metal");
        soundGroupStringHashMap.put(BlockSoundGroup.NETHER_WART, "nether_Wart");
        soundGroupStringHashMap.put(BlockSoundGroup.SAND, "sand");
        soundGroupStringHashMap.put(BlockSoundGroup.SCAFFOLDING, "scaffolding");
        soundGroupStringHashMap.put(BlockSoundGroup.SLIME, "slime");
        soundGroupStringHashMap.put(BlockSoundGroup.SNOW, "snow");
        soundGroupStringHashMap.put(BlockSoundGroup.STEM, "stem");
        soundGroupStringHashMap.put(BlockSoundGroup.STONE, "stone");
        soundGroupStringHashMap.put(BlockSoundGroup.SWEET_BERRY_BUSH, "sweet_berry_bush");
        soundGroupStringHashMap.put(BlockSoundGroup.WET_GRASS, "wet_grass");
        soundGroupStringHashMap.put(BlockSoundGroup.WOOD, "wood");
        soundGroupStringHashMap.put(BlockSoundGroup.WOOL, "wool");

        soundGroupStringHashMap.forEach((soundGroup, s) ->
                materialArgument.then(LiteralArgumentBuilder.<Map<String, Object>>literal(s)
                        .executes(context -> {
                            getSource(context).setSoundGroup(soundGroup);
                            return 1;
                        })
                ));

        return materialArgument;
    }

    private static boolean isNameIllegal(String name) {
        //reserved names that control redstone output.
        return (name.equals("redstone_out") || name.equals("comparator_out"));
    }

}
