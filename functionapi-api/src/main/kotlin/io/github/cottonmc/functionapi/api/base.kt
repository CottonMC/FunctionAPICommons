package io.github.cottonmc.functionapi.api


/**
 * Simple interfaces used to create new behaviours.
 * */

/**
 * When applied to mojang's command source it will add a method to get a clear "cancelled" result to a function file (not just "passed","failed")
 * */
interface CommandSourceExtension {
    val isCancelled: Boolean
        get() = false

    fun cancel()
}

interface ExtendedBlockProperties {
    /**
     * used as an additional way to determine weather or not this block is stairs.
     * Depends on the implementation, if you need to change this behaviour.
     */
    val isBlockStairs: Boolean
        get() = false
}
