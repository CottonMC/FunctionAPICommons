package io.github.cottonmc.functionapi.blocks.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.api.content.BlockTemplate;
import io.github.cottonmc.functionapi.api.content.enums.Direction;
import io.github.cottonmc.functionapi.api.content.enums.PlacementPosition;
import io.github.cottonmc.functionapi.blocks.template.BlockTemplateImpl;
import io.github.cottonmc.functionapi.util.PlacementMapping;

import java.util.Map;


public class BlockPlacementCommand {

    public static void register(CommandDispatcher<Map<String, Object>> commandDispatcher) {
        LiteralArgumentBuilder<Map<String,Object>> blockplacement = LiteralArgumentBuilder.literal("blockplacement");

        for (Direction direction : Direction.values()) {
            LiteralArgumentBuilder<Map<String,Object>> sideBuilder = LiteralArgumentBuilder.literal(direction.name().toLowerCase());

            for (Direction facingDirection : Direction.values()) {

                LiteralArgumentBuilder<Map<String,Object>> placementDirection = LiteralArgumentBuilder.literal(facingDirection.name().toLowerCase());

                for (PlacementPosition placementPosition : PlacementPosition.values()) {
                    placementDirection = placementDirection.then(LiteralArgumentBuilder.<Map<String,Object>>literal(placementPosition.name().toLowerCase())
                            .then(RequiredArgumentBuilder.<Map<String,Object>, String>argument("name", StringArgumentType.string())
                                    .then(RequiredArgumentBuilder.<Map<String,Object>, String>argument("value", StringArgumentType.string())
                                            .executes(context -> {
                                                String name = StringArgumentType.getString(context, "name");

                                                Map<String,Object> source = context.getSource();
                                                String stateValue = StringArgumentType.getString(context, "value");

                                                ((BlockTemplate)source.getOrDefault("block",new BlockTemplateImpl())).addPlacementState(new PlacementMapping(name,stateValue,direction,placementPosition,facingDirection));

                                                return 1;
                                            })
                                    )));
                }
                sideBuilder = sideBuilder.then(placementDirection);
            }
            blockplacement = blockplacement.then(sideBuilder);
        }
        commandDispatcher.register(blockplacement);
    }
}
