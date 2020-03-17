package io.github.cottonmc.functionapi.content.template

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.registration.RegistrationContainer
import io.github.cottonmc.functionapi.util.impl.PermanentHashmap
import java.util.*

class RegistrationTemplate: RegistrationContainer {

    private val identifiers = LinkedList<FunctionAPIIdentifier>()
    private val postfixes = LinkedList<String>()
    private val variants = PermanentHashmap<Int, MutableList<String>>()


    override fun setID(identifier: FunctionAPIIdentifier) {
        identifiers.add(identifier)
    }

    override fun setAddPostfix(postfix: String) {
        postfixes.add(postfix)
    }

    override fun setAddVariant(variantIndex:Int,variant: String) {
        variants.getOrDefault(variantIndex,LinkedList()).add(variant)
    }

    override fun getIDs(): List<FunctionAPIIdentifier> {
        return identifiers
    }

    override fun getPostfixes(): List<String> {
        return postfixes
    }

    override fun getVariants(): Map<Int, List<String>> {
        return variants
    }
}