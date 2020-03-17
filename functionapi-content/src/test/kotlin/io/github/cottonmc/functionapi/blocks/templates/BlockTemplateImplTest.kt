package io.github.cottonmc.functionapi.blocks.templates

import io.github.cottonmc.functionapi.content.StaticCommandExecutor
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.StringWriter

internal class BlockTemplateImplTest {

    @Test
    fun `documentation test`(){
        var template = BlockTemplateImpl()

        val staticCommandExecutor = StaticCommandExecutor()
        staticCommandExecutor.registerDataObject(template)

        val stringWriter = StringWriter()

        staticCommandExecutor.printDocs(stringWriter)

        println(stringWriter)

        val fileContent = BlockTemplateImplTest::class.java.getResource("./blocktemplatedescription.md").readText()
        assertEquals(fileContent,stringWriter.toString())

    }
}