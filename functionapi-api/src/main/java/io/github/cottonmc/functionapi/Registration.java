package io.github.cottonmc.functionapi;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.api.content.*;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.util.FunctionAPIIdentifierImpl;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class Registration implements BiConsumer<CommandFileSource, CommandDispatcher<Map<String,Object>>> {

    private LinkedList<ContentRegistration> contentRegistrations;

    public Registration(){
        contentRegistrations = new LinkedList<>();
        ServiceLoader.load(ContentRegistration.class).iterator().forEachRemaining(contentRegistrations::add);

    }

    private void accept(Map<String,Object> contentRegistrationContext) {
        Map<FunctionAPIIdentifier, ContentRegistrationContext.ContentType> elements = (Map<FunctionAPIIdentifier, ContentRegistrationContext.ContentType>) contentRegistrationContext.get("elements");

        if (elements.isEmpty()) {
            return;
        }

        Map<Integer, List<String>> variants = (Map<Integer, List<String>>) contentRegistrationContext.get("variants");

        if (variants.size() == 0) {
            elements.forEach((identifier, contentType) -> register("", contentRegistrationContext, identifier));
        } else {

            List<List<Object>> permutations = Lists.cartesianProduct(variants.values().toArray(new List[]{}));
            elements.forEach((identifier, contentType) -> {

                for (List<Object> variant : permutations) {
                    String variantName = "_" + variant.stream().map(Object::toString).collect(Collectors.joining("_"));
                   register(variantName,contentRegistrationContext,identifier);
                }
            });
        }
    }

    private void register(String variantName, Map<String, Object> contentRegistrationContext, FunctionAPIIdentifier identifier) {

        identifier = new FunctionAPIIdentifierImpl(identifier.getNamespace(), identifier.getPath() + variantName + contentRegistrationContext.get("postfix"));
        for (ContentRegistration contentRegistration : contentRegistrations) {
            contentRegistration.register(identifier,contentRegistrationContext);
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

}
