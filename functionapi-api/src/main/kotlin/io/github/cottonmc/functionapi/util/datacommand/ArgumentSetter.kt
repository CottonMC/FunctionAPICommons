package io.github.cottonmc.functionapi.util.datacommand

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION,AnnotationTarget.PROPERTY_SETTER)
@Inherited
annotation class ArgumentSetter {
}