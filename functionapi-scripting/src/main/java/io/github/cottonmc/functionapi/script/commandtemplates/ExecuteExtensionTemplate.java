package io.github.cottonmc.functionapi.script.commandtemplates;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;

public abstract class ExecuteExtensionTemplate<S,POS> {
    protected abstract ArgumentType<POS> blockPosArgumentType();

    protected abstract int execute(CommandContext<S> context,String name,String value);

    public void register(CommandDispatcher<S> commandDispatcher) {
        RootCommandNode<S> root = commandDispatcher.getRoot();
        CommandNode<S> child = root.getChild("execute");

        LiteralArgumentBuilder<S> blockstate = LiteralArgumentBuilder.literal("blockstate");

        blockstate
                .then(RequiredArgumentBuilder.<S,String>argument("name", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.<S,String>argument("value", StringArgumentType.string())
                                .then(RequiredArgumentBuilder.<S,POS>argument("position", blockPosArgumentType())
                                        .executes(context -> execute(context, StringArgumentType.getString(context,"name"),StringArgumentType.getString(context,"value")))
                                        .redirect(child))));

        child.getChild("if").addChild(blockstate.build());
    }
}
