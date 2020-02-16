package io.github.cottonmc.functionapi.util

import java.util.*

/**
 * Hashmap that stores the default values you get from it.
 */
class PermanentHashmap<K,V> : HashMap<K, V>() {
    override fun getOrDefault(o: K, o2: V): V {
        val aDefault = super.getOrDefault(o, o2)!!
        return if (this.containsKey(o)) {
            aDefault
        } else {
            this[o] = aDefault
            aDefault
        }
    }
}