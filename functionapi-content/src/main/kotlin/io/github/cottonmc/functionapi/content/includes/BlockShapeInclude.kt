package io.github.cottonmc.functionapi.content.includes

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.FileSource
import io.github.cottonmc.functionapi.api.content.block.BlockState
import io.github.cottonmc.functionapi.api.content.block.CollosionShape
import io.github.cottonmc.functionapi.api.content.block.enums.BlockStateType.*
import io.github.cottonmc.functionapi.api.content.registration.Include
import io.github.cottonmc.functionapi.blocks.templates.BooleanBlockState
import io.github.cottonmc.functionapi.blocks.templates.CustomBlockState
import io.github.cottonmc.functionapi.blocks.templates.IntBlockState

class BlockShapeInclude(val source: FileSource) : Include {
    val gson: Gson

    init {
        gson = GsonBuilder().registerTypeAdapter(BlockState::class.java, BlockstateTypeAdapter()).create()
    }

    override fun include(context: MutableMap<String, Any>, item: FunctionAPIIdentifier) {

        val descriptor = gson.fromJson(source.files[item], CollosionShape::class.java)

        (context.getOrDefault("block_shape", mutableListOf<CollosionShape>())
                as MutableList<CollosionShape>).add(descriptor)
    }

    override fun matches(item: FunctionAPIIdentifier): Boolean {
        return item.path.endsWith(".block_shape")
    }

    class BlockstateTypeAdapter : TypeAdapter<BlockState<*>>() {
        private val gson = Gson()
        override fun write(writer: JsonWriter, value: BlockState<*>) {
            writer.beginObject()
            writer.name("type")
            writer.value(value.type.toString().toLowerCase())
            writer.name("name")
            writer.value(value.name)
            when (value.type) {
                BOOLEAN -> {
                    writer.name("value")
                    writer.value(true)
                }
                INTEGER -> {
                    writer.name("value")
                    writer.value(value.value.toString().toInt())
                }
                CUSTOM -> {
                    writer.name("value")
                    val stringValue = (value as CustomBlockState).value
                    writer.beginArray()
                    for (s in stringValue) {
                        writer.value(s)
                    }
                    writer.endArray()
                }
            }
            writer.endObject()
        }

        override fun read(input: JsonReader): BlockState<*> {
            input.beginObject()

            lateinit var typeName: String
            lateinit var name: String
            lateinit var value: String
            while (input.hasNext()) {
                val peek = input.peek()
                if (peek == JsonToken.NAME) {
                    val fieldName = input.nextName()
                    if (fieldName == "type") {
                        typeName = input.nextString()
                    }
                    if (fieldName == "name") {
                        name = input.nextString()
                    }
                    if (fieldName == "value") {
                        value = input.nextString()
                    }
                }
            }

            return when (valueOf(typeName.toUpperCase())) {
                BOOLEAN -> {
                    BooleanBlockState(name)
                }
                INTEGER -> {
                    IntBlockState(name, value.toInt())
                }
                CUSTOM -> {
                    val values = gson.fromJson(value, MutableList::class.java)
                    CustomBlockState(name, values as MutableList<String>)
                }
            }
        }
    }
}