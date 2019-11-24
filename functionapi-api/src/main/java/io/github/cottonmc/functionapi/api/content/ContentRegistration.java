package io.github.cottonmc.functionapi.api.content;

import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

import java.util.Map;

public interface ContentRegistration {
    void register(FunctionAPIIdentifier identifier, Map<String,Object> context);
}
