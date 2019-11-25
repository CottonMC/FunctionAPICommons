package io.github.cottonmc.functionapi;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.api.content.CommandFileSource;
import io.github.cottonmc.functionapi.api.content.StaticCommandRegistry;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.commands.IncludeCommand;
import io.github.cottonmc.functionapi.commands.PrintCommand;
import io.github.cottonmc.functionapi.util.MissingResourceException;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StaticCommandExecutor {

    public static StaticCommandExecutor INSTANCE = new StaticCommandExecutor();

    private final List<CommandFileSource> commandFileSource;
    private CommandDispatcher<Map<String,Object>> commandDispatcher;
    private Map<FunctionAPIIdentifier, List<String>> commandFiles = new HashMap<>();
    private StaticCommandExecutor() {
        this.commandFileSource = new ArrayList<>();
        ServiceLoader.load(CommandFileSource.class).iterator().forEachRemaining(commandFileSource::add);
        for (CommandFileSource fileSource : commandFileSource) {
            commandFiles.putAll(fileSource.getCommandFiles());
        }


        commandDispatcher = new CommandDispatcher<>();


        PrintCommand.register((CommandDispatcher) commandDispatcher);
        IncludeCommand.register(commandDispatcher);
        ServiceLoader.load(StaticCommandRegistry.class).iterator().forEachRemaining(staticCommandRegistry -> staticCommandRegistry.register(commandDispatcher));
    }

    public void register(Consumer<CommandDispatcher<Map<String,Object>>> command) {
        command.accept(commandDispatcher);
    }

    public void execute(BiConsumer<CommandFileSource,CommandDispatcher<Map<String,Object>>> registrationHandler) {
        System.out.println("Function API starting content registration");
        for (CommandFileSource fileSource : commandFileSource) {
            registrationHandler.accept(fileSource,commandDispatcher);
        }
        System.out.println("Function API finished content registration");
    }

    private ParseResults<Map<String,Object>>parseCommand(String command, Map<String, Object> source){
        return commandDispatcher.parse(command.trim(), source);
    }

    public List<ParseResults<Map<String,Object>>> parseFile(List<String> commands, Map<String,Object> source){
        return commands.stream().map(command->parseCommand(command,source)).collect(Collectors.toList());
    }

    public void execute(List<ParseResults<Map<String,Object>>> commands,Map<String,Object> context) throws CommandSyntaxException {
        for (ParseResults<Map<String,Object>> command : commands) {
            commandDispatcher.execute(new ParseResults<>(command.getContext().withSource(context), command.getReader(), command.getExceptions()));
        }
    }

    public CommandDispatcher<Map<String,Object>> getCommandDispatcher() {
        return commandDispatcher;
    }

    public void execute(FunctionAPIIdentifier identifier, Map<String, Object> source)  throws MissingResourceException, CommandSyntaxException {
        List<String> strings = commandFiles.get(identifier);
        if(strings == null)
        {
            throw new MissingResourceException(identifier);
        }

        for (String string : strings) {
            commandDispatcher.execute(string,source);
        }

    }
}
