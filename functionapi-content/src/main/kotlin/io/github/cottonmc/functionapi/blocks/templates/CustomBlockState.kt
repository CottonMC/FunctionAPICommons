package io.github.cottonmc.functionapi.blocks.templates

import io.github.cottonmc.functionapi.api.content.block.BlockState
import io.github.cottonmc.functionapi.api.content.block.enums.BlockStateType

class CustomBlockState(override val name: String, override val value: MutableList<String>) : BlockState<MutableList<String>> {
    constructor(name:String,value:String):this(name, mutableListOf(value))
    override val type = BlockStateType.CUSTOM
}