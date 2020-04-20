package io.github.cottonmc.functionapi.content

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.FileSource
import io.github.cottonmc.functionapi.api.content.CommandData
import io.github.cottonmc.functionapi.content.commands.PrintCommand
import io.github.cottonmc.functionapi.util.annotation.ArgumentSetter
import io.github.cottonmc.functionapi.util.annotation.Description
import io.github.cottonmc.functionapi.util.annotation.Name
import io.github.cottonmc.functionapi.util.impl.FunctionAPIIdentifierImpl
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.StringWriter
import java.util.function.Consumer

internal class StaticCommandExecutorTest {

    private lateinit var instance: StaticCommandExecutor

    @BeforeEach
    fun setUp() {
        instance = StaticCommandExecutor()
    }

    @Test
    fun execute() {
        var message = ""

        instance.register(PrintCommand(Consumer{ message = it}))
        instance.register(TestSource(mapOf(
                FunctionAPIIdentifierImpl("test","test.mccontent") to "print test"
        )))

        instance.execute(FunctionAPIIdentifierImpl("test","test.mccontent"), mapOf())

        assertEquals("test",message)
    }

    @Test
    fun `execute with an include`(){
        var message = ""

        instance.register(PrintCommand(Consumer{ message = it}))

        instance.register(TestSource(mapOf(
                FunctionAPIIdentifierImpl("test","test.mccontent") to "print test",
                FunctionAPIIdentifierImpl("test","test1.mccontent") to "include test:test.mccontent"
        )))

        instance.execute(FunctionAPIIdentifierImpl("test","test1.mccontent"), mapOf())

        assertEquals("test",message)
    }

    @Test
    fun getIDs() {
    }

    @Name("test")
    @Description("Test command description")
    interface testCommandBase : CommandData {
        var name: String
            @Name("argumentname", valueName = "name", defaultValue = "name") @ArgumentSetter set

        var descriptions: String
            @Name("descripted", valueName = "name", defaultValue = "name") @ArgumentSetter @Description("with a description") set
        var descriptions2: String
            @Name("descripted2", valueName = "name", defaultValue = "name") @ArgumentSetter @Description("second with a description") set

    }

    class TestCommand(override var name: String="name", override var descriptions: String="value", override var descriptions2: String="value") : testCommandBase {
        override fun createNew(): Any {
            return TestCommand()
        }
    }

    internal class TestSource(override val files: Map<FunctionAPIIdentifier, String>) : FileSource {}

    @Test
    fun `can print out the documentation based on the annotations`() {
        instance.registerDataObject(TestCommand())

        val stringWriter = StringWriter()
        instance.printDocs(stringWriter)

        print(stringWriter)

        assertEquals("""Prefab mod content registration command reference:


include:
--------

Includes other files into the script. For other mccontent files it's the same as the 'function' command


`include <id>`



test:
-----

Test command description


`test argumentname <name>`


with a description


`test descripted <name>`


second with a description


`test descripted2 <name>`


""".trimIndent(),stringWriter.toString())
    }
}
