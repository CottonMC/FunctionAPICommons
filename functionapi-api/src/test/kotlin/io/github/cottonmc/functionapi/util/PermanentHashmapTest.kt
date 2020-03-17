package io.github.cottonmc.functionapi.util

import io.github.cottonmc.functionapi.util.impl.PermanentHashmap
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class PermanentHashmapTest {

    private lateinit var instance: PermanentHashmap<String, String>

    @BeforeEach
    fun setUp() {
        instance = PermanentHashmap()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `get or default will store the default`(){

        val value = instance.getOrDefault("key","value")

        assertAll(
                { assertNotNull(value)},
                { assertNotNull(instance.get("key"))}
        )

    }
}