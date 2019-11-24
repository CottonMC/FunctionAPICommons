package io.github.cottonmc.functionapi.script.commandtemplates;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.api.commands.CommandWithTwoArguments;
import io.github.cottonmc.functionapi.api.commands.CommandWithArgument;
import io.github.cottonmc.functionapi.api.content.enums.Direction;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType;

/**
 * Command that manipulates inventories while respecting their properties (unlike the data command)
 * Syntax:
 * inventory amount(int)
 * -> move block/entity block/entity selector(source) block/entity block/entity selector(target)
 * -> item(an item argument)
 * -> move block/entity block/entity selector(source) block/entity block/entity selector(target)
 * -> damage block/entity block/entity selector(source) eventID
 * -> consume block/entity block/entity selector(source) eventID
 * <p>
 * damage and consume fires off an event, if the target item could be damaged or consumed
 * move without a filter takes the first available item.
 * if there is no block with an inventory, then we'll use all of the floating items in that position.
 */
public abstract class InventoryCommandTemplate<S, POS, E, I> {

    protected abstract ArgumentType<POS> blockPosArgumentType();

    protected abstract ArgumentType<E> getEntityArgumentType();

    protected abstract ArgumentType<I> getitemPredicateArgumentType();

    private final CommandWithTwoArguments<S, Direction> moveItemFiltered;
    private final CommandWithTwoArguments<S, Direction> moveItemUnFiltered;
    private final CommandWithArgument<S, Direction> consumeItemBlock;
    private final CommandWithArgument<S, Direction> damageItemBlock;
    private final Command<S> damageItemEntity;
    private final Command<S> consumeItemEntity;

    public InventoryCommandTemplate(CommandWithTwoArguments<S, Direction> moveItemFiltered, CommandWithTwoArguments<S, Direction> moveItemUnFiltered, CommandWithArgument<S, Direction> onsumeItemBlock, CommandWithArgument<S, Direction> damageItemBlock, Command<S> damageItemEntity, Command<S> consumeItemEntity) {
        this.moveItemFiltered = moveItemFiltered;
        this.moveItemUnFiltered = moveItemUnFiltered;
        this.consumeItemBlock = onsumeItemBlock;
        this.damageItemBlock = damageItemBlock;
        this.damageItemEntity = damageItemEntity;
        this.consumeItemEntity = consumeItemEntity;
    }

    public void register(CommandDispatcher<S> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<S>literal("inventory")
                        .then(RequiredArgumentBuilder.<S, Integer>argument("amount", IntegerArgumentType.integer())
                                .then(LiteralArgumentBuilder.<S>literal("move")
                                        .then(buildSourceBlockMoveArgument(moveItemUnFiltered))
                                        .then(buildSourceEntityMoveArgument(moveItemUnFiltered)
                                                .then(RequiredArgumentBuilder.<S, I>argument("item", getitemPredicateArgumentType())
                                                        .then(buildSourceBlockMoveArgument(moveItemFiltered))
                                                        .then(buildSourceEntityMoveArgument(moveItemFiltered)
                                                        )
                                                )))
                                .then(LiteralArgumentBuilder.<S>literal("consume")
                                        .then(RequiredArgumentBuilder.<S, I>argument("item", getitemPredicateArgumentType())
                                                .then(buildSourceBlockUseArgument(consumeItemBlock)
                                                        .then(buildSourceEntityUseArgument(consumeItemEntity)
                                                        )
                                                ))
                                        .then(LiteralArgumentBuilder.<S>literal("damage")
                                                .then(RequiredArgumentBuilder.<S, I>argument("item", getitemPredicateArgumentType())
                                                        .then(buildSourceBlockUseArgument(damageItemBlock))
                                                        .then(buildSourceEntityUseArgument(damageItemEntity)
                                                        )
                                                )
                                        )
                                )));
    }

    private ArgumentBuilder<S, LiteralArgumentBuilder<S>> buildTargetBlockMoveArgument(CommandWithTwoArguments<S, Direction> command, Direction sourceDirection) {
        RequiredArgumentBuilder<S, POS> targetBlock = RequiredArgumentBuilder.argument("targetBlock", blockPosArgumentType());

        for (Direction value : Direction.values()) {
            targetBlock = targetBlock.then(LiteralArgumentBuilder.<S>literal(value.name().toLowerCase())
                    .executes(context -> command.execute(context, sourceDirection, value))
            );
        }

        return LiteralArgumentBuilder
                .<S>literal("block")
                .then(targetBlock);
    }

    private ArgumentBuilder<S, LiteralArgumentBuilder<S>> buildSourceBlockMoveArgument(CommandWithTwoArguments<S, Direction> command) {
        RequiredArgumentBuilder<S, POS> sourceBlock = RequiredArgumentBuilder.argument("sourceBlock", blockPosArgumentType());


        for (Direction value : Direction.values()) {
            sourceBlock = sourceBlock.then(LiteralArgumentBuilder.<S>literal(value.name().toLowerCase())
                    .then(buildTargetBlockMoveArgument(command, value))
                    .then(LiteralArgumentBuilder.<S>literal("entity")
                            .then(RequiredArgumentBuilder.<S, E>argument("targetEntity", getEntityArgumentType())
                                    .executes(context -> command.execute(context, value, null))
                            )
                    )
            );

        }

        return LiteralArgumentBuilder.<S>
                literal("block").then(sourceBlock);
    }

    private ArgumentBuilder<S, LiteralArgumentBuilder<S>> buildSourceBlockUseArgument(CommandWithArgument<S, Direction> command) {
        RequiredArgumentBuilder<S, POS> sourceBlock = RequiredArgumentBuilder.argument("sourceBlock", blockPosArgumentType());

        for (Direction value : Direction.values()) {
            sourceBlock = sourceBlock.then(LiteralArgumentBuilder.<S>literal(value.name().toLowerCase())
                    .then(RequiredArgumentBuilder.<S, FunctionAPIIdentifier>argument("eventName", FunctionAPIIdentifierArgumentType.identifier())
                            //.suggests(EventCommand.SUGGESTION_PROVIDER)
                            .executes(context -> command.execute(context, value)))
                    .executes(context -> command.execute(context, value))
            );
        }

        return LiteralArgumentBuilder
                .<S>literal("block")
                .then(sourceBlock);
    }

    private ArgumentBuilder<S, LiteralArgumentBuilder<S>> buildSourceEntityMoveArgument(CommandWithTwoArguments<S, Direction> command) {
        return LiteralArgumentBuilder.<S>literal("entity")
                .then(RequiredArgumentBuilder.<S, E>argument("sourceEntity", getEntityArgumentType())
                        .then(LiteralArgumentBuilder.<S>literal("entity")
                                .then(RequiredArgumentBuilder.<S, E>argument("targetEntity", getEntityArgumentType())
                                        .executes(context -> command.execute(context, null, null))
                                )));
    }

    private ArgumentBuilder<S, LiteralArgumentBuilder<S>> buildSourceEntityUseArgument(Command<S> command) {
        return LiteralArgumentBuilder.<S>literal("entity")
                .then(RequiredArgumentBuilder.<S, E>argument("sourceEntity", getEntityArgumentType())
                        .then(RequiredArgumentBuilder.<S, FunctionAPIIdentifier>argument("eventName", FunctionAPIIdentifierArgumentType.identifier())
                                //.suggests(EventCommand.SUGGESTION_PROVIDER)
                                .executes(command))
                        .executes(command));
    }


}
