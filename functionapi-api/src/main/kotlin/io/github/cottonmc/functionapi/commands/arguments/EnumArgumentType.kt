package io.github.cottonmc.functionapi.commands.arguments

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier

class EnumArgumentType<T>(private val claz:Class<T>): ArgumentType<T> {
        
        override fun parse(reader: StringReader): T {
            val int_1 = reader.cursor
            while (reader.canRead() && reader.peek().toString().isNotBlank()) {
                reader.skip()
            }
            val input = reader.string.substring(int_1, reader.cursor)

            for (enumConstant in claz.enumConstants) {
                if(enumConstant.toString().toLowerCase() == input)
                    return enumConstant
            }

            val message = LiteralMessage("invalid argument " + input)
            throw CommandSyntaxException(SimpleCommandExceptionType(message), message)

        }

    }
