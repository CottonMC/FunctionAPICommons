package io.github.cottonmc.functionapi.api.content.registration

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.CommandData
import io.github.cottonmc.functionapi.util.annotation.ArgumentSetter
import io.github.cottonmc.functionapi.util.annotation.Context
import io.github.cottonmc.functionapi.util.annotation.Name

@Name("register")
@Context("registration")
interface RegistrationContainer: CommandData {
    @Name("id")
    @ArgumentSetter
    fun setID( @Name("identifier") identifier: FunctionAPIIdentifier)

    @Name("add_postfix")
    @ArgumentSetter
    fun setAddPostfix(@Name("postfix")postfix:String)

    @Name("add_variant")
    @ArgumentSetter
    fun setAddVariant(@Name("variant index") variantIndex: Int, @Name("variant name")variant: String)

    fun getIDs():List<FunctionAPIIdentifier>

    fun getPostfixes():List<String>

    fun getVariants():Map<Int,List<String>>

}