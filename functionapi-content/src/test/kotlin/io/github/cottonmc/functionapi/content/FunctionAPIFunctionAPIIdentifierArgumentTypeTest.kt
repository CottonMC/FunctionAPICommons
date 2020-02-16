package io.github.cottonmc.functionapi.content

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType.Companion.identifier
import io.github.cottonmc.functionapi.util.FunctionAPIIdentifierImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable

internal class FunctionAPIFunctionAPIIdentifierArgumentTypeTest {
    var stringCommandDispatcher: CommandDispatcher<String>? = null
    var identifier: FunctionAPIIdentifier? = null
    @BeforeEach
    @Throws(CommandSyntaxException::class)
    fun setUp() {
        stringCommandDispatcher = CommandDispatcher()
        val register = stringCommandDispatcher!!.register(
                LiteralArgumentBuilder.literal<String>("id")
                        .then(LiteralArgumentBuilder.literal<String>("id")
                                .executes { context: CommandContext<String>? -> 1 })
                        .then(RequiredArgumentBuilder.argument<String, FunctionAPIIdentifier>("identifier", identifier())
                                .executes { context: CommandContext<String> ->
                                    identifier = context.getArgument("identifier", FunctionAPIIdentifierImpl::class.java)
                                    1
                                })
        )
        val allUsage = stringCommandDispatcher!!.getAllUsage(stringCommandDispatcher!!.root, "", false)
        println("usages:")
        for (s in allUsage) {
            println(s)
        }
        stringCommandDispatcher!!.execute("id id", "")
    }

    @Test
    @DisplayName("The argument can be read as an functionAPIIdentifier.")
    @Throws(CommandSyntaxException::class)
    fun canReadArgument() {
        stringCommandDispatcher!!.execute("id namespace:value", "")
        Assertions.assertAll(
                Executable { Assertions.assertEquals("namespace", identifier!!.namespace) },
                Executable { Assertions.assertEquals("value", identifier!!.path) }
        )
    }

    @AfterEach
    fun tearDown() {
    }
}