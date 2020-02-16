package io.github.cottonmc.functionapi.api

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE,AnnotationTarget.FIELD)
annotation class Title(val value:String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE,AnnotationTarget.FIELD)
annotation class Description(val value:String)