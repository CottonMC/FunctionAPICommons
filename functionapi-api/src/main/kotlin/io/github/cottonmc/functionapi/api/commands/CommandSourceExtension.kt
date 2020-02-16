package io.github.cottonmc.functionapi.api.commands

interface CommandSourceExtension {
    val isCancelled: Boolean
        get() = false

    fun cancel()
}