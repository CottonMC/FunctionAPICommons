package io.github.cottonmc.functionapi.util.annotation

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION,AnnotationTarget.PROPERTY_GETTER,AnnotationTarget.PROPERTY)
@Inherited
annotation class ArgumentGetter {
}