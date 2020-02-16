package io.github.cottonmc.functionapi.api.content

import io.github.cottonmc.functionapi.api.content.enums.Material
import io.github.cottonmc.functionapi.util.PlacementMapping
import org.apache.commons.lang3.tuple.Pair

interface BlockTemplate {
    fun setHardness(level: Int)
    fun setHasItem(b: Boolean)
    fun setTicksRandomly(b: Boolean)
    fun createIntProperty(name: String?, i1: Int)
    fun setCanSpawnMobs(b: Boolean)
    fun createBooleanProperty(name: String?)
    fun addToStringProperty(name: String?, value: String?)
    fun addCollosionShape(name: String?, value: String?, collosonShape: io.github.cottonmc.functionapi.api.content.CollosonShape?)
    fun makeStairs()
    var material: io.github.cottonmc.functionapi.api.content.enums.Material?
    val lightLevel: Int
    var isCollidable: Boolean
    fun getHardness(): Float
    var soundGroup: io.github.cottonmc.functionapi.api.content.enums.BlockSoundGroup?
    var tool: io.github.cottonmc.functionapi.api.content.enums.Tools?
    var miningLevel: Int
    fun ticksRandomly(): Boolean
    fun hasItem(): Boolean
    val isStairs: Boolean
    var isAir: Boolean
    fun canSpawnMobs(): Boolean
    var isInvisible: Boolean
    fun addPlacementState(placementMapping: PlacementMapping?)
    val placementStates: List<Any?>?
    val properties: Map<String?, Pair<Any?, Any?>?>?
    var renderLayer: io.github.cottonmc.functionapi.api.content.enums.BlockRenderLayer?
    fun makeWaterlogged()
    val isWaterlogged: Boolean
}