package io.github.cottonmc.functionapi.util.datacommand

import java.lang.annotation.Inherited


@Target(AnnotationTarget.CLASS)
@Inherited
annotation class Context(val name:String){
    
}