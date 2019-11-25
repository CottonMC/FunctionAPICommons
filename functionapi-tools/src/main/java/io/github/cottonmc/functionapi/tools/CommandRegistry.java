package io.github.cottonmc.functionapi.tools;

import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.functionapi.api.content.StaticCommandRegistry;
import io.github.cottonmc.functionapi.tools.commands.EntityAttributeCommand;
import io.github.cottonmc.functionapi.tools.commands.ItemSettingsCommand;
import io.github.cottonmc.functionapi.tools.commands.RegistrationCommand;

import java.util.Map;

@SuppressWarnings("unchecked")
public class CommandRegistry implements StaticCommandRegistry {
    @Override
    public void register(CommandDispatcher<Map<String,Object>> dispatcher) {
        ItemSettingsCommand.register(dispatcher);
        EntityAttributeCommand.register(dispatcher);
        RegistrationCommand.register(dispatcher);
    }
}
