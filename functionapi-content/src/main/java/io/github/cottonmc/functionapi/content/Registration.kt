package io.github.cottonmc.functionapi.content

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.CommandFileSource
import io.github.cottonmc.functionapi.api.content.CommandRegistrator
import io.github.cottonmc.functionapi.api.content.ContentRegistration
import io.github.cottonmc.functionapi.content.commands.PrintCommand
import io.github.cottonmc.functionapi.content.commands.RegistrationCommand
import io.github.cottonmc.functionapi.content.documentation.ContentCommandDocumentationGenerator
import io.github.cottonmc.functionapi.util.FunctionAPIIdentifierImpl
import io.github.cottonmc.functionapi.util.MissingResourceException
import io.github.cottonmc.functionapi.util.PermanentHashmap
import java.util.function.Consumer

class Registration {
    private var context= PermanentHashmap<String, Any>()
    fun register(subfolder: String, contentRegistrations: List<ContentRegistration>, commandRegistrators: List<CommandRegistrator>, commandFileSources: List<CommandFileSource>): CommandDispatcher<Map<String, Any>> {

        context = PermanentHashmap()
        val staticCommandExecutor = StaticCommandExecutor()

        staticCommandExecutor.apply{
            for (commandRegistrator in commandRegistrators) {
                register(commandRegistrator)
            }
            for (commandFileSource in commandFileSources) {
                register(commandFileSource)
            }
            register(RegistrationCommand())
            register(PrintCommand())

            ContentCommandDocumentationGenerator().generate(commandDispatcher,subfolder)
        }

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
                for (contentRegistration in contentRegistrations) {
                    contentRegistration.register(context as Map<String, Any>)
                }
            }
        })

        return staticCommandExecutor.commandDispatcher
    }
}