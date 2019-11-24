package io.github.cottonmc.functionapi.api.content;


import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

import java.util.List;
import java.util.Map;

public interface CommandFileSource {

    Map<FunctionAPIIdentifier, List<String>> getCommandFiles();
}
