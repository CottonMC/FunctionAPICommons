package io.github.cottonmc.functionapi.blocks;

import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.functionapi.api.content.StaticCommandRegistry;
import io.github.cottonmc.functionapi.blocks.commands.BlockPlacementCommand;
import io.github.cottonmc.functionapi.blocks.commands.BlockSettingsCommand;
import io.github.cottonmc.functionapi.blocks.commands.RegistrationCommand;
import io.github.cottonmc.functionapi.commands.IncludeCommand;
import io.github.cottonmc.functionapi.commands.PrintCommand;

import java.util.Map;

@SuppressWarnings("unchecked")
public class CommandRegistry implements StaticCommandRegistry {
    @Override
    public void register(CommandDispatcher<Map<String,Object>> dispatcher) {
        BlockPlacementCommand.register(dispatcher);
        BlockSettingsCommand.register(dispatcher);
        RegistrationCommand.register(dispatcher);
        PrintCommand.register((CommandDispatcher) dispatcher);
        IncludeCommand.register((CommandDispatcher) dispatcher);
    }
}
