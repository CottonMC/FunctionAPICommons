package io.github.cottonmc.functionapi.util.annotation

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION,AnnotationTarget.PROPERTY_SETTER,AnnotationTarget.PROPERTY)
@Inherited
annotation class ArgumentSetter(val isVisibleInUI:Boolean = true) {
}