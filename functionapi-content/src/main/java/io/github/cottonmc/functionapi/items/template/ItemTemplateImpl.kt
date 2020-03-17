package io.github.cottonmc.functionapi.items.template

import io.github.cottonmc.functionapi.api.content.item.ItemTemplate
import io.github.cottonmc.functionapi.api.content.item.enums.CreativeTabs
import io.github.cottonmc.functionapi.api.content.item.enums.ItemType

class ItemTemplateImpl(override var maxCount: Int = 64,
                       override var type: ItemType = ItemType.NORMAL,
                       override var maxUseTime: Int = 6000,
                       override var creativeTab: CreativeTabs = CreativeTabs.MISCELLANEOUS
) : ItemTemplate