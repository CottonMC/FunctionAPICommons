package io.github.cottonmc.functionapi.content

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.CommandFileSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StaticCommandExecutorTest {

    private lateinit var instance :StaticCommandExecutor

    @BeforeEach
    fun setUp() {
        instance = StaticCommandExecutor()
    }

    @Test
    fun execute() {


    }

    @Test
    fun getIDs() {
    }

    fun `print included by default`(){

    }


    internal class TestCommandSource(override val commandFiles: Map<FunctionAPIIdentifier, List<String>>):CommandFileSource{}
}


