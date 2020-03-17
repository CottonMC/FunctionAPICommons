package io.github.cottonmc.functionapi.content

import io.github.cottonmc.functionapi.api.content.registration.RegistrationContainer
import io.github.cottonmc.functionapi.content.commands.PrintCommand
import io.github.cottonmc.functionapi.content.template.RegistrationTemplate
import io.github.cottonmc.functionapi.util.impl.FunctionAPIIdentifierImpl
import junit.framework.Assert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.StringWriter
import java.io.Writer

import java.util.function.Consumer

internal class RegistrationTest {
    private lateinit var executor: StaticCommandExecutor
    private lateinit var instance: Registration
    private lateinit var writer: Writer

    private lateinit var reg: RegistrationContainer

    @BeforeEach
    fun setUp() {
        writer = StringWriter()
        instance = Registration("type", writer)
        executor = instance.staticCommandExecutor
        reg = RegistrationTemplate()
        executor.registerDataObject(reg)
    }

    @Test
    fun register() {
        var message = ""

        executor.register(PrintCommand(Consumer { message = it }))
        executor.register(StaticCommandExecutorTest.TestSource(mapOf(
                FunctionAPIIdentifierImpl("test", "type/test.mccontent") to "print test",
                FunctionAPIIdentifierImpl("test", "type/ignored/test.mccontent") to "print test",
                FunctionAPIIdentifierImpl("test", "random/test.mccontent") to "print not running")
        ))

        instance.register(emptyList())

        assertEquals("test", message)
    }
}