package io.github.cottonmc.functionapi.api.content.block

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.CommandData
import io.github.cottonmc.functionapi.util.annotation.Context
import io.github.cottonmc.functionapi.util.annotation.Name

@Name("hitbox")
@Context("hitbox")
interface BlockHitbox: CommandData {

    @Name("add")
    fun addHitbox(@Name("state",valueName = "A blockstate formatted ") state:MutableList<BlockState<*>>,id:FunctionAPIIdentifier)
}