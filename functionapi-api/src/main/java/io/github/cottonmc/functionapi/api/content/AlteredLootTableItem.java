package io.github.cottonmc.functionapi.api.content;

import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

public interface AlteredLootTableItem {

    String getTableID();

    default boolean hasAlteredLootTabe(){
        return getTableID() != null;
    }
}
