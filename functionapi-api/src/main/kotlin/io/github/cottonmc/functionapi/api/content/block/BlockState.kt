package io.github.cottonmc.functionapi.api.content.block

import io.github.cottonmc.functionapi.api.content.block.enums.BlockStateType

interface BlockState<T> {
    val type: BlockStateType
    val name:String
    var value:T
}