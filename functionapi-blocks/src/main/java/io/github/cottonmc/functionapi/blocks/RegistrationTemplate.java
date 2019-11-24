package io.github.cottonmc.functionapi.blocks;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.api.content.*;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.util.FunctionAPIIdentifierImpl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class RegistrationTemplate implements BiConsumer<CommandFileSource, CommandDispatcher<Map<String,Object>>> {

    private void accept(Map<String,Object> contentRegistrationContext) {
        Map<FunctionAPIIdentifier, ContentRegistrationContext.ContentType> elements = (Map<FunctionAPIIdentifier, ContentRegistrationContext.ContentType>) contentRegistrationContext.get("elements");

        if (elements.isEmpty()) {
            return;
        }

        Map<Integer, List<String>> variants = (Map<Integer, List<String>>) contentRegistrationContext.get("variants");

        if (variants.size() == 0) {
            elements.forEach((identifier, contentType) -> register("", contentRegistrationContext, identifier, contentType));
        } else {

            List<List<Object>> permutations = Lists.cartesianProduct(variants.values().toArray(new List[]{}));
            elements.forEach((identifier, contentType) -> {

                for (List<Object> variant : permutations) {
                    String variantName = "_" + variant.stream().map(Object::toString).collect(Collectors.joining("_"));
                    register(variantName, contentRegistrationContext, identifier, contentType);
                }
            });
        }
    }

    private void register(String variantName, Map<String, Object> contentRegistrationContext, FunctionAPIIdentifier identifier, ContentRegistrationContext.ContentType contentType) {
        BlockTemplate blockTemplate = (BlockTemplate) contentRegistrationContext.get("block");
        ItemTemplate itemTemplate = (ItemTemplate) contentRegistrationContext.get("item");

        identifier = new FunctionAPIIdentifierImpl(identifier.getNamespace(), identifier.getPath() + variantName + contentRegistrationContext.get("postfix"));

        if (contentType == ContentRegistrationContext.ContentType.BLOCK) {
            if (blockTemplate.hasItem()) {
                registerBlock(blockTemplate,itemTemplate,identifier);
            }
        }
        if (contentType == ContentRegistrationContext.ContentType.ITEM) {
            registerItem(itemTemplate, (ItemMaterial) contentRegistrationContext.get("item_material"),identifier);
        }
    }


    @Override
    public void accept(CommandFileSource commandFileSource, CommandDispatcher<Map<String,Object>> contentRegistrationContextCommandDispatcher) {
        Map<FunctionAPIIdentifier, List<String>> commands = commandFileSource.getCommandFiles();


        commands.forEach((identifier, list) -> {
            Map<String,Object> context = new HashMap<>();

            if (identifier.getPath().split("/").length == 1) {
                try {
                    for (String command : list) {
                        contentRegistrationContextCommandDispatcher.execute(command, context);
                    }
                } catch (CommandSyntaxException e) {
                    System.err.println(e.getInput());
                    e.printStackTrace();
                }

                accept(context);
            }

        });
    }

    protected abstract void registerBlock(BlockTemplate blockTemplate, ItemTemplate itemTemplate, FunctionAPIIdentifier identifier);
    protected abstract void registerItem(ItemTemplate template, ItemMaterial itemMaterial, FunctionAPIIdentifier identifier);

}
