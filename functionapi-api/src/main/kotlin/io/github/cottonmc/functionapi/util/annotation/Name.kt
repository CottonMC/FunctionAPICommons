package io.github.cottonmc.functionapi.util.annotation

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION,AnnotationTarget.VALUE_PARAMETER,AnnotationTarget.CLASS,AnnotationTarget.FIELD,AnnotationTarget.PROPERTY_SETTER,AnnotationTarget.PROPERTY)
@Inherited
annotation class Name(val name:String,val displayName:String ="",val valueName:String="",val defaultValue:String=""){

    companion object{

        fun getValueName(name:Name): String {
            if(name.defaultValue.isBlank())
            return name.valueName
            return name.valueName + " (default:${name.defaultValue})"
        }

        fun getDisplayName(name:Name): String {
            if(name.displayName.isBlank()){
                return name.name.capitalize()
            }
            return name.displayName
        }
    }
}