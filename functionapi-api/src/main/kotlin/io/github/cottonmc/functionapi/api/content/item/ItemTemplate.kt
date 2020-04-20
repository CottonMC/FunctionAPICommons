package io.github.cottonmc.functionapi.api.content.item

import io.github.cottonmc.functionapi.api.content.item.enums.CreativeTabs
import io.github.cottonmc.functionapi.api.content.item.enums.ItemType
import io.github.cottonmc.functionapi.api.content.CommandData
import io.github.cottonmc.functionapi.util.annotation.ArgumentGetter
import io.github.cottonmc.functionapi.util.annotation.ArgumentSetter
import io.github.cottonmc.functionapi.util.annotation.Context
import io.github.cottonmc.functionapi.util.annotation.Name

/**
 * Stores enough data to create a new item.
 */
@Name("itemsettings")
@Context("item")
interface ItemTemplate : CommandData {
    @Name("stack_size", "amount")
    @ArgumentGetter
    @ArgumentSetter
    var maxCount: Int

    @Name("item_type", "type")
    @ArgumentGetter
    @ArgumentSetter
    var type: ItemType

    @Name("max_use_time", "use time")
    @ArgumentGetter
    @ArgumentSetter
    var maxUseTime: Int

    @Name("creative_tab", "tab")
    @ArgumentGetter
    @ArgumentSetter
    var creativeTab: CreativeTabs
}