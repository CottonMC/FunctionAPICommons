package io.github.cottonmc.functionapi.blocks.templates

import io.github.cottonmc.functionapi.api.content.block.BlockState
import io.github.cottonmc.functionapi.api.content.block.BlockTemplate
import io.github.cottonmc.functionapi.api.content.block.enums.BlockRenderLayer
import io.github.cottonmc.functionapi.api.content.block.enums.BlockSoundGroup
import io.github.cottonmc.functionapi.api.content.block.enums.Material
import io.github.cottonmc.functionapi.api.content.item.enums.Tools
import java.lang.UnsupportedOperationException
import java.util.*

class BlockTemplateImpl(override var hardness: Float = 1f,
                        override var hasItem: Boolean = true,
                        override var ticksRandomly: Boolean = false,
                        override var canSpawnMobs: Boolean = true,
                        override var isStairs: Boolean = false,
                        override var material: Material = Material.STONE,
                        override var lightLevel: Int = 0,
                        override var isCollidable: Boolean = true,
                        override var soundGroup: BlockSoundGroup = BlockSoundGroup.STONE,
                        override var tool: Tools = Tools.HANDS,
                        override var miningLevel: Int = 0,
                        override var isAir: Boolean = false,
                        override var isInvisible: Boolean = false,
                        override var isWaterlogged: Boolean = false,
                        override var renderLayer: BlockRenderLayer = BlockRenderLayer.SOLID,
                        override var resistance: Float =0f
) : BlockTemplate {

    private val states = LinkedList<BlockState<*>>()

    override fun createIntProperty(name: String, value: Int) {
        checkStateName(name)
        states.add(IntBlockState(name,value))
    }

    override fun createBooleanProperty(name: String) {
        checkStateName(name)
        states.add(BooleanBlockState(name))
    }

    private fun checkStateName(name:String){
        val firstOrNull = states.firstOrNull { it.name == name }
        if(firstOrNull != null){
            throw UnsupportedOperationException("State with name $name is already exists! This is not allowed!")
        }
    }

    override fun addToStringProperty(name: String, value: String) {
        var stateSet=false

        states.forEach {
            if(it.name == name){
                if(it is CustomBlockState){
                    it.value.add(value)
                    stateSet = true
                }else{
                    throw UnsupportedOperationException("State with name $name is not a custom blockstate! This is not allowed!")
                }
            }
        }
        if(!stateSet){
            states.add(CustomBlockState(name,value))
        }
    }

    override fun getStates(): Array<BlockState<*>> {
        return states.toTypedArray()
    }
}