package io.github.cottonmc.functionapi.util.datacommand

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION,AnnotationTarget.VALUE_PARAMETER,AnnotationTarget.CLASS,AnnotationTarget.FIELD,AnnotationTarget.PROPERTY_SETTER)
@Inherited
annotation class Name(val name:String,val valueName:String=""){
    
}