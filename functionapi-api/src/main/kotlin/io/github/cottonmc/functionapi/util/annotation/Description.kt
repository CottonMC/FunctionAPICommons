package io.github.cottonmc.functionapi.util.annotation

@Target(AnnotationTarget.PROPERTY,AnnotationTarget.PROPERTY_SETTER,AnnotationTarget.FUNCTION,AnnotationTarget.TYPE,AnnotationTarget.CLASS)
annotation class Description(val description:String)