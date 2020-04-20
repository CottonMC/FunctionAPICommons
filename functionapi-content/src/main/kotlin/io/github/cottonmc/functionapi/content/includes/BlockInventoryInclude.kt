package io.github.cottonmc.functionapi.content.includes

import com.google.gson.Gson
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.FileSource
import io.github.cottonmc.functionapi.api.content.inventory.BlockInventoryDescriptor
import io.github.cottonmc.functionapi.api.content.registration.Include

class BlockInventoryInclude(val source:FileSource):Include {

    val gson = Gson()
    override fun include(context: MutableMap<String, Any>, item: FunctionAPIIdentifier) {
        val descriptor = gson.fromJson(source.files[item], BlockInventoryDescriptor::class.java)
        context["block_inventory"] = descriptor
    }

    override fun matches(item: FunctionAPIIdentifier): Boolean {
        return item.path.endsWith(".block_inv")
    }
}