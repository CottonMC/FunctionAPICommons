package io.github.cottonmc.functionapi.util.commandbuilder

import io.github.cottonmc.functionapi.util.annotation.ArgumentGetter
import io.github.cottonmc.functionapi.util.annotation.Name
import io.github.cottonmc.functionapi.util.getAnnotations

object PrintDataClassAsCommands {

    fun print(data: Any): String {
        var output = ""

        val getters = data::class.java.methods.filter { it.isAnnotationPresent(ArgumentGetter::class.java) }

        val commandName = getAnnotations(data, Name::class.java).get()

        for (getter in getters) {
            val name = getAnnotations(getter, Name::class.java)
            if (name.isPresent) {
                val name = name.get()

                if (getter.returnType.isArray) {
                    for (s in (getter.invoke(data) as Array<String>)) {
                        output += "${commandName.name} ${name.name} ${s.toLowerCase()}"
                    }
                } else {
                    output += "${commandName.name} ${name.name} ${getter.invoke(data).toString().toLowerCase()}"
                }
            }
        }
        return output
    }
}