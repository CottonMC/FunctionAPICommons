package io.github.cottonmc.functionapi.util.annotation

import java.lang.annotation.Inherited


@Target(AnnotationTarget.CLASS)
@Inherited
annotation class Context(val name:String){
    
}