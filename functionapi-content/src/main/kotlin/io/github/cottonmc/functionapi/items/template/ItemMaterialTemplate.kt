package io.github.cottonmc.functionapi.items.template

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.item.ItemMaterial
import io.github.cottonmc.functionapi.api.content.item.enums.EquipSoundEvent
import io.github.cottonmc.functionapi.util.impl.FunctionAPIIdentifierImpl

data class ItemMaterialTemplate(override var toughness: Float =1f,
                                override var miningSpeed: Float=1f,
                                override var attackDamage: Float=1f,
                                override var miningLevel: Int =1,
                                override var repairIngredient: FunctionAPIIdentifier = FunctionAPIIdentifierImpl(),
                                override var equipSound: EquipSoundEvent = EquipSoundEvent.GENERIC,
                                override var enchantability: Int = 1,
                                override var durability: Int =100
) : ItemMaterial {

    override fun createNew(): Any {
        return ItemMaterialTemplate()
    }
}