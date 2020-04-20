package io.github.cottonmc.functionapi.api.content.item

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier
import io.github.cottonmc.functionapi.api.content.item.enums.EquipSoundEvent
import io.github.cottonmc.functionapi.api.content.CommandData
import io.github.cottonmc.functionapi.util.annotation.ArgumentGetter
import io.github.cottonmc.functionapi.util.annotation.ArgumentSetter
import io.github.cottonmc.functionapi.util.annotation.Context
import io.github.cottonmc.functionapi.util.annotation.Name

@Name("itemmaterial")
@Context("item-material")
interface ItemMaterial : CommandData {

    @Name("enchantability", valueName = "amount")
    @ArgumentSetter
    @ArgumentGetter
    var enchantability: Int


    @Name("equip_sound")
    @ArgumentSetter
    @ArgumentGetter
    var equipSound: EquipSoundEvent


    @Name("repairmaterial", valueName = "item tag")
    @ArgumentSetter
    @ArgumentGetter
    var repairIngredient: FunctionAPIIdentifier
        @ArgumentSetter set

    @Name("toughness", valueName = "amount")
    @ArgumentSetter
    @ArgumentGetter
    var toughness: Float


    @Name("mining_speed", valueName = "amount")
    @ArgumentSetter
    @ArgumentGetter
    var miningSpeed: Float


    @Name("attack_damage", valueName = "amount")
    @ArgumentSetter
    @ArgumentGetter
    var attackDamage: Float


    @Name("mining_level", valueName = "amount")
    @ArgumentSetter
    @ArgumentGetter
    var miningLevel: Int


    @Name("durability", valueName = "amount")
    @ArgumentSetter
    @ArgumentGetter
    var durability: Int

}