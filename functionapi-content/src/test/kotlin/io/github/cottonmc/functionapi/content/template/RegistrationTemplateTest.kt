package io.github.cottonmc.functionapi.content.template

import io.github.cottonmc.functionapi.content.StaticCommandExecutor
import io.github.cottonmc.functionapi.content.commands.RegistrationCommand
import io.github.cottonmc.functionapi.content.documentation.ContentCommandDocumentationGenerator
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.StringWriter

internal class RegistrationTemplateTest {

    lateinit var executor:StaticCommandExecutor
    lateinit var registrationCommand:RegistrationCommand
    @BeforeEach
    fun setUp() {
        executor = StaticCommandExecutor()
        registrationCommand = RegistrationCommand()
        executor.register(registrationCommand)
    }

    @Test
    fun `shows up in the documentation`(){
        val stringWriter = StringWriter()
        ContentCommandDocumentationGenerator().generate(executor.commandDispatcher,stringWriter)

        assertTrue(stringWriter.buffer.toString().contains("register id <identifier>"),"'register id <identifier>' not found in: \n ${stringWriter.buffer}")
    }
}