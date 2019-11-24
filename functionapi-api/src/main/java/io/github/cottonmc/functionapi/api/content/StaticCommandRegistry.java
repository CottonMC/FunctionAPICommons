package io.github.cottonmc.functionapi.api.content;

import com.mojang.brigadier.CommandDispatcher;

import java.util.Map;

/**
 * Used as a service to register commands into the common executor.
 * */
public interface StaticCommandRegistry {
    void register(CommandDispatcher<Map<String,Object>> dispatcher);
}
