package io.github.cottonmc.functionapi.util

import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.util.*


fun <T : Annotation> getAnnotations(obj:Any, annotation: Class<T>): Optional<T> {
    return getAnnotations(obj::class.java,annotation)
}


/**
 * Travel all parent classes to get the annotation
 * */
fun <T : Annotation> getAnnotations(clazz: Class<*>, annotation: Class<T>): Optional<T> {
    if (clazz.isAnnotationPresent(annotation)) {
        return Optional.of(clazz.getAnnotation(annotation))
    } else {
        val superclass = clazz.superclass
        if(superclass!= null) {
            val optional1 = getAnnotations(superclass, annotation)
            if (optional1.isPresent)
                return optional1

        }
        for (face in clazz.interfaces) {
            val optional = getAnnotations(face, annotation)
            if (optional.isPresent)
                return optional
        }
    }
    return Optional.empty()
}



/**
 * Travel the parent methods
 * */
fun <T : Annotation> getAnnotations(method: Method, annotation: Class<T>): Optional<T> {
    if (method.isAnnotationPresent(annotation)) {
        return Optional.of(method.getAnnotation(annotation))
    } else {
        val superclass = method.declaringClass.superclass

        if(superclass != null) {
            for (superMethod in superclass.methods) {
                if (superMethod.name == method.name) {
                    val superAnnotation = getAnnotations(superMethod, annotation)
                    if (superAnnotation.isPresent)
                        return superAnnotation
                    break
                }
            }
        }
        for (face in method.declaringClass.interfaces) {
            for (faceMethod in face.methods) {
                if (faceMethod.name == method.name) {
                    val optional = getAnnotations(face.getMethod(method.name, *method.parameterTypes), annotation)
                    if (optional.isPresent)
                        return optional
                    break
                }
            }
        }
    }
    return Optional.empty()
}

fun <T : Annotation> getAnnotations(parameter: Parameter, annotation: Class<T>): Optional<T> {

    if (parameter.isAnnotationPresent(annotation)) {
        return Optional.of(parameter.getAnnotation(annotation))
    } else {
        val declaringExecutable = parameter.declaringExecutable
        var index = 0
        for (methodParameter in declaringExecutable.parameters) {
            if (methodParameter.name == parameter.name) {
                break
            }
            index++
        }
        val superclass = declaringExecutable.declaringClass.superclass

        if(superclass != null) {
            for (method in superclass.methods) {
                if (method.name == declaringExecutable.name) {
                    for (methodParameter in method.parameters) {
                        if (methodParameter.name == parameter.name) {
                            return getAnnotations(methodParameter, annotation)
                        }
                    }
                }
            }
        }

        for (face in declaringExecutable.declaringClass.interfaces) {
            for (method in face.methods) {
                if (method.name == declaringExecutable.name) {
                    for (methodParameter in method.parameters) {
                        if (methodParameter.name == parameter.name) {
                            return getAnnotations(methodParameter, annotation)
                        }
                    }
                }
            }
        }

    }
    return Optional.empty()
}

