package io.github.cottonmc.functionapi.api.content.block

import io.github.cottonmc.functionapi.util.commandbuilder.annotation.ArgumentFormat

@ArgumentFormat("[name=value,name2=value2]")
interface BlockStateMap {
    var states:MutableList<BlockState<*>>
}