package io.github.cottonmc.functionapi.content.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandExceptionType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.CommandRegistrator
import io.github.cottonmc.functionapi.api.content.registration.Include
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType
import io.github.cottonmc.functionapi.util.MissingResourceException
import io.github.cottonmc.functionapi.util.annotation.Description
import io.github.cottonmc.functionapi.util.annotation.Name
import java.util.*

@Name("include")
@Description("Includes other files into the script. For other mccontent files it's the same as the 'function' command")
class IncludeCommand : CommandRegistrator {
    private val includes = LinkedList<Include>();

    fun addInclude(include:Include){
        includes.add(include)
    }

    override fun register(commandDispatcher: CommandDispatcher<MutableMap<String, Any>>) {
        commandDispatcher.register(LiteralArgumentBuilder.literal<MutableMap<String, Any>>("include")
                .then(RequiredArgumentBuilder.argument<MutableMap<String, Any>, FunctionAPIIdentifier>("id", FunctionAPIIdentifierArgumentType.identifier())
                        .executes { context: CommandContext<MutableMap<String, Any>> ->
                            val functionAPIIdentifier = context.getArgument("id", FunctionAPIIdentifier::class.java)
                            try {
                                var included = false
                                for (include in includes) {
                                    if(include.matches(functionAPIIdentifier)){
                                        include.include(context.source,functionAPIIdentifier)
                                        included = true
                                        break
                                    }
                                }
                                if(!included)
                                    throw CommandSyntaxException(InvalidInclude.instance) {"No include directives could match this id!"}
                            } catch (e: MissingResourceException) {
                                throw CommandSyntaxException(InvalidInclude.instance) { "Static resource $functionAPIIdentifier is missing!" }

                            }
                            1
                        }
                ))
    }

    private class InvalidInclude:CommandExceptionType{
        companion object{
            val instance = InvalidInclude()
        }
    }
}