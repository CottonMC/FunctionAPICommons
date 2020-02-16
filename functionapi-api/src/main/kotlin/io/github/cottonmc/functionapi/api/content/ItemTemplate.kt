package io.github.cottonmc.functionapi.api.content

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.enums.CreativeTabs
import io.github.cottonmc.functionapi.api.content.enums.ItemType
import io.github.cottonmc.functionapi.util.datacommand.ArgumentSetter
import io.github.cottonmc.functionapi.util.datacommand.Context
import io.github.cottonmc.functionapi.util.datacommand.Name

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