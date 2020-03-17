package io.github.cottonmc.functionapi.util

import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Name
import java.lang.reflect.Method
import java.util.*

fun getMethodByName(target:Any,name:String):Optional<Method>{

    val clazz = target::class.java

    for (method in clazz.methods) {
        val optional = getAnnotations(method, Name::class.java)
        if(optional.isPresent){
            if(optional.get().name == name){
                return Optional.of(method)
            }
        }
    }

    return Optional.empty()
}