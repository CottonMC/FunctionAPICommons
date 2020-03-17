package io.github.cottonmc.functionapi.api.content.block

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Context
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Name

@Name("hitbox")
@Context("hitbox")
interface BlockHitbox {

    @Name("add")
    fun addHitbox(@Name("state",valueName = "A blockstate formatted ") state:BlockStateMap,id:FunctionAPIIdentifier)
}