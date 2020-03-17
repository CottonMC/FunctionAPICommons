package io.github.cottonmc.functionapi.util.commandbuilder

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.commands.arguments.EnumArgumentType
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType
import java.util.*

fun <T> classToArgument(claz: Class<T>): Optional<ArgumentType<Any>> {

    return Optional.ofNullable(when {
        claz == Int::class.java -> {
            IntegerArgumentType.integer()
        }
        claz == Float::class.java -> {
            FloatArgumentType.floatArg()
        }
        claz == FunctionAPIIdentifier::class.java -> {
            FunctionAPIIdentifierArgumentType.identifier()
        }
        claz.isEnum ->{
            EnumArgumentType(claz)
        }
        else -> {
            StringArgumentType.string()
        }
    } as ArgumentType<Any>)
}