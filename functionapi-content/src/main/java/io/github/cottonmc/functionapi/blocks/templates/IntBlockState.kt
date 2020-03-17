package io.github.cottonmc.functionapi.blocks.templates

import io.github.cottonmc.functionapi.api.content.block.BlockState
import io.github.cottonmc.functionapi.api.content.block.enums.BlockStateType

class IntBlockState( override val name: String, override var value: Int) : BlockState<Int> {
    override val type = BlockStateType.INTEGER
}