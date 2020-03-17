package io.github.cottonmc.functionapi.blocks.templates

import io.github.cottonmc.functionapi.api.content.block.BlockState
import io.github.cottonmc.functionapi.api.content.block.enums.BlockStateType

class BooleanBlockState(override val name: String) : BlockState<Boolean> {
    override val type = BlockStateType.BOOLEAN
    override var value = true
        set(value) {
            throw UnsupportedOperationException("The boolean blockstate has no value to change!")
        }
}