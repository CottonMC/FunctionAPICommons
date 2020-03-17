package io.github.cottonmc.functionapi.util.impl

import java.util.*

/**
 * Hashmap that stores the default values you get from it.
 */
class PermanentHashmap<K,V> : HashMap<K, V>() {
    override fun getOrDefault(key: K, defaultValue: V): V {
        val aDefault = super.getOrDefault(key, defaultValue)!!
        return if (this.containsKey(key)) {
            aDefault
        } else {
            this[key] = aDefault
            aDefault
        }
    }
}