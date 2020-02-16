package io.github.cottonmc.functionapi.content.items.template

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.ItemMaterial
import io.github.cottonmc.functionapi.api.content.enums.EquipSoundEvent
import io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot
import io.github.cottonmc.functionapi.util.datacommand.Context
import io.github.cottonmc.functionapi.util.FunctionAPIIdentifierImpl
import io.github.cottonmc.functionapi.util.datacommand.Name
import io.github.cottonmc.functionapi.util.datacommand.ArgumentSetter
import java.util.*

data class ItemMaterialTemplate(override var toughness: Float =1f,
                                override var miningSpeed: Float=1f,
                                override var attackDamage: Float=1f,
                                override var miningLevel: Int =1,
                                override var repairIngredient: FunctionAPIIdentifier = FunctionAPIIdentifierImpl(),
                                override var equipSound: EquipSoundEvent = EquipSoundEvent.GENERIC,
                                override var enchantability: Int = 1,
                                override var durability: Int =100
) : ItemMaterial {

}