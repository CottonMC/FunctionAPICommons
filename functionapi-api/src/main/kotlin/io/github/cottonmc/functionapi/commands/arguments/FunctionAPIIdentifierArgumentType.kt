package io.github.cottonmc.functionapi.commands.arguments

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.util.impl.FunctionAPIIdentifierImpl

class FunctionAPIIdentifierArgumentType : ArgumentType<FunctionAPIIdentifier> {

    @Throws(CommandSyntaxException::class)
    override fun parse(reader: StringReader): FunctionAPIIdentifier {
        val int_1 = reader.cursor
        while (reader.canRead() && FunctionAPIIdentifier.isCharValid(reader.peek())) {
            reader.skip()
        }
        val input = reader.string.substring(int_1, reader.cursor)
        val split = input.split(":").toTypedArray()
        if (split.size != 2) {
            val message = LiteralMessage("invalid argument " + input)
            throw CommandSyntaxException(SimpleCommandExceptionType(message), message)
        }
        return FunctionAPIIdentifierImpl(split[0], split[1])
    }

    companion object {
        fun identifier(): FunctionAPIIdentifierArgumentType {
            return FunctionAPIIdentifierArgumentType()
        }
    }
}