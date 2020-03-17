package io.github.cottonmc.functionapi.content

import com.google.common.collect.Lists
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.ContentRegistration
import io.github.cottonmc.functionapi.api.content.registration.RegistrationContainer
import io.github.cottonmc.functionapi.content.template.RegistrationTemplate
import io.github.cottonmc.functionapi.util.documentation.ContentCommandDocumentationGenerator
import io.github.cottonmc.functionapi.util.impl.FunctionAPIIdentifierImpl
import io.github.cottonmc.functionapi.util.MissingResourceException
import io.github.cottonmc.functionapi.util.impl.PermanentHashmap
import java.io.Writer
import java.util.ArrayList
import java.util.function.Consumer
import java.util.stream.Collectors

class Registration(private val subfolder: String,
                   private var documentationGenerator: ContentCommandDocumentationGenerator = ContentCommandDocumentationGenerator(subfolder)
) {

    constructor(subfolder: String,writer: Writer):this(subfolder,ContentCommandDocumentationGenerator(writer))
    private var context = PermanentHashmap<String, Any>()
    val staticCommandExecutor = StaticCommandExecutor()

    fun register(contentRegistrations: List<ContentRegistration>): CommandDispatcher<Map<String, Any>> {

        context = PermanentHashmap()

        documentationGenerator.generate(staticCommandExecutor.commandDispatcher)

        val commands: Set<FunctionAPIIdentifier> = staticCommandExecutor.iDs
        commands.forEach(Consumer { identifier: FunctionAPIIdentifier ->
            if (identifier.path.startsWith("$subfolder/") && identifier.path.split("/").toTypedArray().size == 2 && identifier.path.endsWith(".mccontent")) {
                try {
                    staticCommandExecutor.execute(FunctionAPIIdentifierImpl(identifier.namespace, identifier.path), context)
                } catch (e: CommandSyntaxException) {
                    e.printStackTrace()
                } catch (e: MissingResourceException) {
                    e.printStackTrace()
                }
                generateIdsAndRegister(contentRegistrations, context)
            }
        })

        return staticCommandExecutor.commandDispatcher
    }

    private fun generateIdsAndRegister(contentRegistrations: List<ContentRegistration>, context: Map<String, Any>) {
        contentRegistrations.forEach { it.setup(context) }
        val registrationContainer = context.getOrDefault("registration",RegistrationTemplate()) as RegistrationContainer

        val ids = registrationContainer.getIDs()
        var postfix = (registrationContainer.getPostfixes()).stream().collect(Collectors.joining("_"))
        if (!postfix.isEmpty()) {
            postfix = "_$postfix"
        }
        val variants = (registrationContainer.getVariants())
        val variantNames: Collection<List<String>> = Lists.cartesianProduct(ArrayList(variants.values))

        if (!variantNames.isEmpty()) {
            for (variantName in variantNames) {
                var variant = variantName.stream().collect(Collectors.joining("_"))
                variant += postfix
                for (functionAPIIdentifier in ids) {
                    contentRegistrations.forEach { it.register(FunctionAPIIdentifierImpl(functionAPIIdentifier.namespace, "${functionAPIIdentifier.path}_$variant")) }
                }
            }
        } else {
            for (functionAPIIdentifier in ids) {
                contentRegistrations.forEach { it.register(functionAPIIdentifier) }
            }
        }
    }
}