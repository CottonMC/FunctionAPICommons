package io.github.cottonmc.functionapi.util

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.commands.arguments.EnumArgumentType
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType
import io.github.cottonmc.functionapi.util.annotation.getAnnotations
import io.github.cottonmc.functionapi.util.datacommand.ArgumentSetter
import io.github.cottonmc.functionapi.util.datacommand.Context
import io.github.cottonmc.functionapi.util.datacommand.Name
import java.lang.reflect.Method
import java.lang.reflect.Parameter

private typealias SetterFunction = (context: Map<String, Any>, arguments: Array<Any>) -> Int

/**
 * convert a data class into a data filling command
 * */
object DataClassToCommand {


    fun getContext(name: String, context: Map<String, Any>, target: Any): Any {
        return context.getOrDefault(name, target)
    }

    fun registerBackedCommand(target: Any, dispatcher: CommandDispatcher<Map<String, Any>>) {
        val nameAnnotation = getAnnotations(target.javaClass, Name::class.java)
        val contextAnnotation = getAnnotations(target.javaClass, Context::class.java)
        val name = if (nameAnnotation.isPresent) {
            nameAnnotation.get().name
        } else {
            target.javaClass.simpleName
        }
        val context = if (contextAnnotation.isPresent) {
            contextAnnotation.get().name
        } else {
            target.javaClass.simpleName
        }

        registerBackedCommand(target, dispatcher, name, context)
    }

    fun registerBackedCommand(target: Any, dispatcher: CommandDispatcher<Map<String, Any>>, name: String, contextName: String) {

        val commandNode = LiteralArgumentBuilder.literal<Map<String, Any>>(name)

        val methods = target::class.java.methods

        for (method in methods) {
            val optionalSetter = getAnnotations(method, ArgumentSetter::class.java)
            if (optionalSetter.isPresent && method.parameterCount > 0) {
                buildMethod(commandNode, method, contextName, target)
            }
        }
        dispatcher.register(commandNode)
    }

    class InvalidStateException(clas: Class<out Any>, method: Method) : Exception("Invalid data class: ${clas.canonicalName}, method ${method.name} is invalid!")


    private fun buildMethod(commandNode: LiteralArgumentBuilder<Map<String, Any>>, method: Method, contextName: String, target: Any) {

        val optionalName = getAnnotations(method, Name::class.java)

        val fieldName = if (optionalName.isPresent) {
            optionalName.get().name
        } else {
            method.name.removePrefix("set")
        }

        val arrayOfParameters = method.parameters

        val parameter = arrayOfParameters[0]
        when (arrayOfParameters.size) {
            1 -> {
                commandNode.then(fieldForOneArgument(method, fieldName, parameter) { context: Map<String, Any>, argument: Array<Any> ->
                    method.invoke(getContext(contextName, context, target), argument[0])
                    1
                })
            }
            2 -> {
                commandNode.then(fieldForTwoArgument(fieldName, parameter, arrayOfParameters[1]) { context: Map<String, Any>, argument: Array<Any> ->
                    method.invoke(getContext(contextName, context, target), argument[0], argument[1])
                    1
                })
            }
            else -> {
                throw InvalidStateException(target::class.java, method)
            }
        }
    }

    private fun fieldForOneArgument(method: Method, fieldName: String, parameter: Parameter, setter: SetterFunction): LiteralArgumentBuilder<Map<String, Any>> {
        val fieldLiteral: LiteralArgumentBuilder<Map<String, Any>> = LiteralArgumentBuilder.literal<Map<String, Any>>(fieldName)

        val optionalName = getAnnotations(method, Name::class.java)

        val optionalParameterName = getAnnotations(parameter, Name::class.java)
        val paramName =
                if (optionalName.isPresent) {
                    val valueName = optionalName.get().valueName
                    if (valueName.isBlank()) {
                        if (optionalParameterName.isPresent) {
                            optionalParameterName.get().name
                        } else {
                            parameter.name.toLowerCase()
                        }
                    } else {
                        valueName
                    }
                } else if (optionalParameterName.isPresent) {
                    optionalParameterName.get().name
                } else {
                    parameter.name.toLowerCase()
                }


        fieldLiteral.then(RequiredArgumentBuilder.argument<Map<String, Any>, Any>(paramName, classToArgument(parameter.type))
                .executes { context ->
                    val argument = context.getArgument(paramName, parameter.type)

                    setter.invoke(context.source, arrayOf(argument))
                })

        return fieldLiteral
    }

    private fun fieldForTwoArgument(fieldName: String, parameter: Parameter, parameter1: Parameter, setter: SetterFunction): LiteralArgumentBuilder<Map<String, Any>> {
        val optionalParameterName = getAnnotations(parameter1, Name::class.java)

        val parameterName =
                if (optionalParameterName.isPresent) {
                    optionalParameterName.get().name
                } else {
                    parameter1.name.toLowerCase()
                }
        val argumentType = classToArgument(parameter1.type)
        val fieldLiteral: LiteralArgumentBuilder<Map<String, Any>> = LiteralArgumentBuilder.literal<Map<String, Any>>(fieldName)

        if (parameter.type.isEnum) {
            val enumConstants = parameter.type.enumConstants

            for (enumConstant in enumConstants) {
                val enumName = enumConstant.toString().toLowerCase()

                val enumArgument: LiteralArgumentBuilder<Map<String, Any>> = LiteralArgumentBuilder.literal<Map<String, Any>>(enumName)

                enumArgument.then(RequiredArgumentBuilder.argument<Map<String, Any>, Any>(parameterName, argumentType)
                        .executes { context ->
                            val argument = context.getArgument(parameterName, parameter1.type)
                            setter.invoke(context.source, arrayOf(enumConstant, argument))
                        })
                fieldLiteral.then(enumArgument)
            }
        } else {
            val optionalParameterName0 = getAnnotations(parameter, Name::class.java)

            val argumentType0 = classToArgument(parameter.type)
            val parameterName0 =
                    if (optionalParameterName0.isPresent) {
                        optionalParameterName0.get().name
                    } else {
                        parameter.name.toLowerCase()
                    }

            fieldLiteral
                    .then(RequiredArgumentBuilder.argument<Map<String, Any>, Any>(parameterName0, argumentType0)
                            .then(RequiredArgumentBuilder.argument<Map<String, Any>, Any>(parameterName, argumentType)
                                    .executes { context ->
                                        val argument = context.getArgument(parameterName0, parameter.type)
                                        val argument1 = context.getArgument(parameterName, parameter1.type)
                                        setter.invoke(context.source, arrayOf(argument, argument1))
                                    }))
        }
        return fieldLiteral
    }

    private fun <T> classToArgument(claz: Class<T>): ArgumentType<Any> {

        return when {
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
        } as ArgumentType<Any>
    }
}