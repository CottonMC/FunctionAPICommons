package io.github.cottonmc.functionapi.api

import com.google.gson.JsonObject

interface Stack {
    /**
     * If it's an insert, it will search for a tag, if it's an extract then it will return this item.
     * */
    val item:String
    /**
     * Only works during an extract, the stacksize
     * */
    val count:Int
    /**
     * the nbt tag of the item that we return or accept.
     * */
    val nbt:JsonObject
}