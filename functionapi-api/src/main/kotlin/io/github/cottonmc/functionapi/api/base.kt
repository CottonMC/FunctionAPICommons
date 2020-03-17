package io.github.cottonmc.functionapi.api

import io.github.cottonmc.functionapi.api.content.item.ItemTemplate

/**
 * Simple interfaces used to create new behaviours.
 * */

interface CommandSourceExtension {
    val isCancelled: Boolean
        get() = false

    fun cancel()
}

interface ExtendedBlockProperties {
    /**
     * used as an additional way to determine weather or not this block is stairs.
     */
    val isBlockStairs: Boolean
        get() = false
}

interface IncludeCommandRunner {
    fun runCommand(functionAPIIdentifier: FunctionAPIIdentifier?)
}


interface Templated<T> {
    fun setTemplate(template: T)
}

interface UseItemTemplate : Templated<ItemTemplate?>