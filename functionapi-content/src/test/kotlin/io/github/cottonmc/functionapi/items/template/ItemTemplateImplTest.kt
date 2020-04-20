package io.github.cottonmc.functionapi.items.template

import io.github.cottonmc.functionapi.api.content.item.ItemTemplate
import io.github.cottonmc.functionapi.api.content.item.enums.ItemType
import io.github.cottonmc.functionapi.content.StaticCommandExecutor
import io.github.cottonmc.functionapi.content.StaticCommandExecutorTest
import io.github.cottonmc.functionapi.util.documentation.ContentCommandDocumentationGenerator
import io.github.cottonmc.functionapi.util.impl.FunctionAPIIdentifierImpl
import io.github.cottonmc.functionapi.util.impl.PermanentHashmap
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import java.io.StringWriter

internal class ItemTemplateImplTest {

    lateinit var executor: StaticCommandExecutor
    lateinit var template: ItemTemplate
    @BeforeEach
    fun setUp() {
        template = ItemTemplateImpl()
        executor = StaticCommandExecutor()
        executor.registerDataObject(template)
    }

    @Test
    fun `print docs`(){
        val stringWriter = StringWriter()
        val contentCommandDocumentationGenerator = ContentCommandDocumentationGenerator(stringWriter)
        contentCommandDocumentationGenerator.generate(executor.commandDispatcher)

        assertEquals("""Function api registration command reference: 
include <id>
itemsettings creative_tab brewing
itemsettings creative_tab building_blocks
itemsettings creative_tab decorations
itemsettings creative_tab foodstuffs
itemsettings creative_tab miscellaneous
itemsettings creative_tab redstone
itemsettings creative_tab tools
itemsettings creative_tab transportation
itemsettings creative_tab weapons
itemsettings item_type axe
itemsettings item_type bow
itemsettings item_type chestplate
itemsettings item_type crossbow
itemsettings item_type helmet
itemsettings item_type hoe
itemsettings item_type leggings
itemsettings item_type normal
itemsettings item_type pickaxe
itemsettings item_type shield
itemsettings item_type shovel
itemsettings item_type sword
itemsettings item_type tool
itemsettings max_use_time <use time>
itemsettings stack_size <amount>
""",stringWriter.toString())
    }

    @ParameterizedTest(name="{index}: can set the count to {0}")
    @ValueSource(ints = [1, 2, 45, 16])
    @DisplayName("can set the count")
    fun count(count: Int) {
        executor.register(StaticCommandExecutorTest.TestSource(mapOf(
                FunctionAPIIdentifierImpl("test", "test.mccontent") to "itemsettings stack_size $count"
        )))

        val context = PermanentHashmap<String,Any>()
        executor.execute(FunctionAPIIdentifierImpl("test", "test.mccontent"), context)

        assertEquals(count, (context["item"] as ItemTemplate).maxCount)
    }

    @ParameterizedTest(name="{index}: can set the type to {0}")
    @EnumSource(ItemType::class)
    @DisplayName("can set the type")
    fun type(type: ItemType) {
        val typeArgumentName = type.name.toLowerCase()
        println("type argument: $typeArgumentName")

        executor.register(StaticCommandExecutorTest.TestSource(mapOf(
                FunctionAPIIdentifierImpl("test", "test.mccontent") to "itemsettings item_type $typeArgumentName"
        )))

        val context = PermanentHashmap<String,Any>()
        executor.execute(FunctionAPIIdentifierImpl("test", "test.mccontent"), context)

        assertEquals(type, (context["item"] as ItemTemplate).type)
    }

}