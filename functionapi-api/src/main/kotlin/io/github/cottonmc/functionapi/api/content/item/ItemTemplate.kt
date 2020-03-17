package io.github.cottonmc.functionapi.api.content.item

import io.github.cottonmc.functionapi.api.content.item.enums.CreativeTabs
import io.github.cottonmc.functionapi.api.content.item.enums.ItemType
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.ArgumentSetter
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Context
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Name

/**
 * Stores enough data to create a new item.
 */
@Name("itemsettings")
@Context("item")
interface ItemTemplate {
    var maxCount: Int
        @Name("stack_size", "amount") @ArgumentSetter set
    var type: ItemType
        @Name("type", "type") @ArgumentSetter set
    var maxUseTime: Int
        @Name("max_use_time", "use time") @ArgumentSetter set
    var creativeTab: CreativeTabs
        @Name("creative_tab", "tab") @ArgumentSetter set
}