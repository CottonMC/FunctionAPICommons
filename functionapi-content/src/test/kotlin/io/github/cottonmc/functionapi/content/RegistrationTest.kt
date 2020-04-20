package io.github.cottonmc.functionapi.content

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.ContentRegistration
import io.github.cottonmc.functionapi.api.content.registration.RegistrationContainer
import io.github.cottonmc.functionapi.content.commands.PrintCommand
import io.github.cottonmc.functionapi.content.template.RegistrationTemplate
import io.github.cottonmc.functionapi.util.impl.FunctionAPIIdentifierImpl
import junit.framework.Assert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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

        executor.register(PrintCommand(Consumer { message += it + "\n" }))
        executor.register(StaticCommandExecutorTest.TestSource(mapOf(
                FunctionAPIIdentifierImpl("test", "type/lib/test.mccontent") to "print test\nregister id test:block",
                FunctionAPIIdentifierImpl("test", "type/test1.mccontent") to "include test:type/lib/test.mccontent",
                FunctionAPIIdentifierImpl("test", "type/ignored/test.mccontent") to "print test",
                FunctionAPIIdentifierImpl("test", "random/test.mccontent") to "print not running")
        ))

        instance.register(emptyList())
        assertEquals("test\n", message)
        assertTrue((instance.context["registration"] as RegistrationContainer).getIDs().contains(FunctionAPIIdentifierImpl("test", "block")))
    }

    @Test
    fun registerMultipleRoot() {

        executor.register(StaticCommandExecutorTest.TestSource(mapOf(
                FunctionAPIIdentifierImpl("test", "type/lib/test.mccontent") to "register id test:block",
                FunctionAPIIdentifierImpl("test", "type/test1.mccontent") to "include test:type/lib/test.mccontent",
                FunctionAPIIdentifierImpl("test", "type/test2.mccontent") to "register id test:block2",
                FunctionAPIIdentifierImpl("test", "type/ignored/test.mccontent") to "print test",
                FunctionAPIIdentifierImpl("test", "random/test.mccontent") to "print not running")
        ))

        var runs = 0
        instance.register(listOf(RegistrationTester(
                Pair(
                        Consumer {
                            val container = it["registration"] as RegistrationContainer
                            val iDs = container.getIDs()
                            assertEquals(1, iDs.size)
                            runs++

                        },
                        Consumer {

                        }))
        ))

        assertEquals(2, runs)
    }

    class RegistrationTester(private val tests: Pair<Consumer<Map<String, Any>>, Consumer<FunctionAPIIdentifier>>) : ContentRegistration {
        override fun setup(context: Map<String, Any>) {
            tests.first.accept(context)
        }

        override fun register(id: FunctionAPIIdentifier) {
            tests.second.accept(id)
        }
    }
}