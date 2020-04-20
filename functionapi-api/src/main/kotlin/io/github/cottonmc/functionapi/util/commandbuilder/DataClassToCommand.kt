package io.github.cottonmc.functionapi.util.commandbuilder

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import io.github.cottonmc.functionapi.api.content.CommandData
import io.github.cottonmc.functionapi.util.annotation.ArgumentSetter
import io.github.cottonmc.functionapi.util.annotation.Context
import io.github.cottonmc.functionapi.util.annotation.Name
import io.github.cottonmc.functionapi.util.getAnnotations
import java.lang.reflect.Method
import java.lang.reflect.Parameter

private typealias SetterFunction = (context: MutableMap<String, Any>, arguments: Array<Any>) -> Int

/**
 * convert a data class into a data filling command
 * */
object DataClassToCommand {

    fun getContext(name: String, context: MutableMap<String, Any>, target: CommandData): Any {
        return context.getOrDefault(name, target.createNew())
    }

    fun registerBackedCommand(target: CommandData, dispatcher: CommandDispatcher<MutableMap<String, Any>>) {
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

    fun registerBackedCommand(target: CommandData, dispatcher: CommandDispatcher<MutableMap<String, Any>>, name: String, contextName: String) {

        val commandNode = LiteralArgumentBuilder.literal<MutableMap<String, Any>>(name)

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


    private fun buildMethod(commandNode: LiteralArgumentBuilder<MutableMap<String, Any>>, method: Method, contextName: String, target: CommandData) {

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
                commandNode.then(fieldForOneArgument(method, fieldName, parameter) { context: MutableMap<String, Any>, argument: Array<Any> ->
                    method.invoke(getContext(contextName, context, target), *argument)
                    1
                })
            }
            2 -> {
                commandNode.then(fieldForTwoArgument(fieldName, parameter, arrayOfParameters[1]) { context: MutableMap<String, Any>, argument: Array<Any> ->
                    method.invoke(getContext(contextName, context, target), *argument)
                    1
                })
            }
            else -> {
                throw InvalidStateException(target::class.java, method)
            }
        }
    }

    private fun fieldForOneArgument(method: Method, fieldName: String, parameter: Parameter, setter: SetterFunction): LiteralArgumentBuilder<MutableMap<String, Any>> {
        val fieldLiteral: LiteralArgumentBuilder<MutableMap<String, Any>> = LiteralArgumentBuilder.literal<MutableMap<String, Any>>(fieldName)

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

        if(parameter.type.isEnum){
            val enumConstants = parameter.type.enumConstants
            for (enumConstant in enumConstants) {
                val enumName = enumConstant.toString().toLowerCase()

                val enumArgument: LiteralArgumentBuilder<MutableMap<String, Any>> = LiteralArgumentBuilder.literal<MutableMap<String, Any>>(enumName)

                enumArgument
                        .executes { context ->
                            setter.invoke(context.source, arrayOf(enumConstant))
                        }
                fieldLiteral.then(enumArgument)
            }
        }else{
            fieldLiteral.then(RequiredArgumentBuilder.argument<MutableMap<String, Any>, Any>(paramName, classToArgument(parameter.type).get())
                    .executes { context ->
                        val argument = context.getArgument(paramName, parameter.type)

                        setter.invoke(context.source, arrayOf(argument))
                    })
        }

        return fieldLiteral
    }

    private fun fieldForTwoArgument(fieldName: String, parameter: Parameter, parameter1: Parameter, setter: SetterFunction): LiteralArgumentBuilder<MutableMap<String, Any>> {
        val optionalParameterName = getAnnotations(parameter1, Name::class.java)

        val parameterName =
                if (optionalParameterName.isPresent) {
                    optionalParameterName.get().name
                } else {
                    parameter1.name.toLowerCase()
                }
        val optionalArgumentType = classToArgument(parameter1.type)

        val fieldLiteral: LiteralArgumentBuilder<MutableMap<String, Any>> = LiteralArgumentBuilder.literal<MutableMap<String, Any>>(fieldName)

        if (parameter.type.isEnum) {
            val enumConstants = parameter.type.enumConstants

            for (enumConstant in enumConstants) {
                val enumName = enumConstant.toString().toLowerCase()

                val enumArgument: LiteralArgumentBuilder<MutableMap<String, Any>> = LiteralArgumentBuilder.literal<MutableMap<String, Any>>(enumName)

                enumArgument.then(RequiredArgumentBuilder.argument<MutableMap<String, Any>, Any>(parameterName, optionalArgumentType.get())
                        .executes { context ->
                            val argument = context.getArgument(parameterName, parameter1.type)
                            setter.invoke(context.source, arrayOf(enumConstant, argument))
                        })
                fieldLiteral.then(enumArgument)
            }
        } else {
            val optionalParameterName0 = getAnnotations(parameter, Name::class.java)

            val optionalArgumentType0 = classToArgument(parameter.type)

            val parameterName0 =
                    if (optionalParameterName0.isPresent) {
                        optionalParameterName0.get().name
                    } else {
                        parameter.name.toLowerCase()
                    }

            if(optionalArgumentType0.isPresent) {
                val argumentType0 = optionalArgumentType0.get();
                fieldLiteral
                        .then(RequiredArgumentBuilder.argument<MutableMap<String, Any>, Any>(parameterName0, argumentType0)
                                .then(RequiredArgumentBuilder.argument<MutableMap<String, Any>, Any>(parameterName, optionalArgumentType.get())
                                        .executes { context ->
                                            val argument = context.getArgument(parameterName0, parameter.type)
                                            val argument1 = context.getArgument(parameterName, parameter1.type)
                                            setter.invoke(context.source, arrayOf(argument, argument1))
                                        }))
            }

        }
        return fieldLiteral
    }

}