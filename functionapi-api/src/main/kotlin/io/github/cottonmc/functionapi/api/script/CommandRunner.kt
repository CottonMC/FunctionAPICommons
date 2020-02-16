package io.github.cottonmc.functionapi.api.script

interface CommandRunner<C, S> {
    fun fire(context: C)
    fun reload(minecraftServer: S)
    fun markDirty()
    fun hasScripts(): Boolean
}