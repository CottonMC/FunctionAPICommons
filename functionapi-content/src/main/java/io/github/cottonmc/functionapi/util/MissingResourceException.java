package io.github.cottonmc.functionapi.util;

import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

public class MissingResourceException extends Exception {
    public MissingResourceException(FunctionAPIIdentifier identifier){
        super("Resource with id "+identifier.asString()+" not found!");

    }
}
