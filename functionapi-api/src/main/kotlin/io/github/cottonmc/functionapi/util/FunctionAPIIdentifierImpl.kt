package io.github.cottonmc.functionapi.util

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier

import java.util.Objects

class FunctionAPIIdentifierImpl(override val namespace: String = "minecraft", override val path: String = "air") : FunctionAPIIdentifier {


    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || o !is FunctionAPIIdentifier) return false
        val that = o
        return namespace == that.namespace && path == that.path
    }

    override fun hashCode(): Int {
        return Objects.hash(namespace, path)
    }

    override fun toString(): String {
        return "$namespace:$path"
    }
}
