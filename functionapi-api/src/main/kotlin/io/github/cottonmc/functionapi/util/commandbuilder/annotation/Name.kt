package io.github.cottonmc.functionapi.util.commandbuilder.annotation

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION,AnnotationTarget.VALUE_PARAMETER,AnnotationTarget.CLASS,AnnotationTarget.FIELD,AnnotationTarget.PROPERTY_SETTER)
@Inherited
annotation class Name(val name:String,val valueName:String="",val defaultValue:String=""){

    companion object{

        fun getValueName(name:Name): String {
            if(name.defaultValue.isBlank())
            return name.valueName
            return name.valueName + " (default:${name.defaultValue})"
        }
    }
}