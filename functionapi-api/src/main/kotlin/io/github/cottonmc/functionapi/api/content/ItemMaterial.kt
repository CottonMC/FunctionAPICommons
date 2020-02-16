package io.github.cottonmc.functionapi.api.content

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.enums.EquipSoundEvent
import io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot
import io.github.cottonmc.functionapi.util.datacommand.ArgumentSetter
import io.github.cottonmc.functionapi.util.datacommand.Context
import io.github.cottonmc.functionapi.util.datacommand.Name

@Name("itemmaterial")
@Context("item-material")
interface ItemMaterial {

    var enchantability: Int
        @ArgumentSetter @Name("enchantability", valueName = "amount") set
    var equipSound: EquipSoundEvent
        @ArgumentSetter @Name("equip_sound") set
    var repairIngredient: FunctionAPIIdentifier
        @ArgumentSetter @Name("repairmaterial", valueName = "tag") set
    var toughness: Float
        @ArgumentSetter @Name("toughness", valueName = "amount") set
    var miningSpeed: Float
        @ArgumentSetter @Name("mining_speed", valueName = "amount") set
    var attackDamage: Float
        @ArgumentSetter @Name("attack_damage", valueName = "amount") set
    var miningLevel: Int
        @ArgumentSetter @Name("mining_level", valueName = "amount") set
    var durability: Int
        @ArgumentSetter @Name("durability", valueName = "amount") set
}