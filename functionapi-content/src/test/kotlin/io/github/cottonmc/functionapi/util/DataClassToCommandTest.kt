package io.github.cottonmc.functionapi.util

import com.mojang.brigadier.CommandDispatcher
import io.github.cottonmc.functionapi.content.StaticCommandExecutor
import io.github.cottonmc.functionapi.content.documentation.ContentCommandDocumentationGenerator
import io.github.cottonmc.functionapi.util.datacommand.ArgumentSetter
import io.github.cottonmc.functionapi.util.datacommand.Context
import io.github.cottonmc.functionapi.util.datacommand.Name
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.StringWriter

internal class DataClassToCommandTest {

    lateinit var dispatcher: CommandDispatcher<Map<String, Any>>

    @BeforeEach
    fun setUp() {
        dispatcher = CommandDispatcher()


    }

    @ParameterizedTest(name = "{index} converting {0} to command")
    @ValueSource(classes = [
        TestData::class,
        TestData2::class,
        TestData3::class,
        TestDataExtraField::class,
        TestData4::class,
        AnnotatedParent::class
    ])
    fun `generates the expected commands`(testData: Class<Any>) {
        val createInstance = testData.newInstance()
        DataClassToCommand.registerBackedCommand(createInstance, dispatcher)
        val writer = StringWriter()

        ContentCommandDocumentationGenerator().generate(dispatcher, writer)
        val toString = writer.buffer.toString()
        assertEquals(createInstance.toString(), toString.split("\n", limit = 2)[1].trim())
    }

    @Test
    fun executeCommand() {
        val data = TestData4()

        val staticCommandExecutor = StaticCommandExecutor()
        DataClassToCommand.registerBackedCommand(data, staticCommandExecutor.commandDispatcher)

        val context = PermanentHashmap<String, Any>()
        staticCommandExecutor.execute("testcommand_field field 1", context)

        assertAll(
                { assertNotNull(context["test"]) },
                { assertEquals("1", data.test) }
        )
    }

    @Name("testcommand")
    @Context("test")
    internal class TestData {
        @Name("name")
        @ArgumentSetter
        fun setTest(@Name("value") int: Int) {
        }

        override fun toString(): String {
            return "testcommand name <value>"
        }
    }

    @Name("testcommand")
    @Context("test")
    internal class TestDataExtraField() {
        @Name("name")
        @ArgumentSetter
        fun setTest(@Name("value") int: Int) {
        }

        fun ignored() {}

        override fun toString(): String {
            return "testcommand name <value>"
        }
    }

    @Name("testcommand2arguments")
    @Context("test")
    internal class TestData2 {
        @Name("name")
        @ArgumentSetter
        fun setTest(@Name("key") int: Int, @Name("value") value: String) {
        }

        override fun toString(): String {
            return "testcommand2arguments name <key> <value>"
        }
    }

    @Name("testcommand2arguments_enum")
    @Context("test")
    internal class TestData3 {

        internal enum class Key {
            key1, key2
        }

        @Name("name")
        @ArgumentSetter
        fun setTest(@Name("key") key: Key, @Name("value") value: String) {
        }

        override fun toString(): String {
            return "testcommand2arguments_enum name key1 <value>\ntestcommand2arguments_enum name key2 <value>"
        }
    }

    @Name("testcommand_field")
    @Context("test")
    internal class TestData4 {

        var test: String = ""
            @Name("field", valueName = "value") @ArgumentSetter set

        override fun toString(): String {
            return "testcommand_field field <value>"
        }
    }

    @Name("test")
    @Context("test")
    internal interface ParentWithAnnotations {
        var test: String
            @Name("field", valueName = "value") @ArgumentSetter set
    }


    internal class AnnotatedParent : ParentWithAnnotations {
        override var test: String = ""

        override fun toString(): String {
            return "test field <value>"
        }
    }
}