package io.github.cottonmc.functionapi.content.includes

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.CommandExecutor
import io.github.cottonmc.functionapi.api.content.registration.Include

class StaticCommandInclude(private val executor: CommandExecutor,private val extension:String):Include {

    override fun include(context: MutableMap<String, Any>, item: FunctionAPIIdentifier) {
        executor.execute(item,context)
    }

    override fun matches(item: FunctionAPIIdentifier): Boolean {
        return item.path.endsWith(".$extension")
    }
}