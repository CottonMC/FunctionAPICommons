package io.github.cottonmc.functionapi.api.content.inventory

import io.github.cottonmc.functionapi.api.Stack
import io.github.cottonmc.functionapi.api.content.Range
import io.github.cottonmc.functionapi.api.content.block.BlockState
import io.github.cottonmc.functionapi.api.content.block.enums.Direction
import io.github.cottonmc.functionapi.api.content.inventory.enums.Action
import io.github.cottonmc.functionapi.api.content.CommandData

interface BlockInventoryDescriptor: CommandData {
    /**
     * The maximum size of the inventory
     * */
    val size: Int
    /**
     *
     * */
    val actions:List<InventoryAction>
    /**
     * weather or not we can interact with the inventory from the outside
     * */
    val blocked:Boolean
    /**
     * If this inventory should be stored as data or not.
     * */
    val store:Boolean
}

interface InventoryAction{
    /**
     * the method that we use in this action
     * */
    val method:Action
    /**
     * The state that the block has to be in, in order to use this action.
     * If empty, then it will be always active.
     * */
    val requiredState:MutableList<BlockState<*>>
    /**
     * What sides this action affects.
     * */
    val side:Array<Direction>
    /**
     * The name of the event to be fired when the action triggers
     * */
    val event:String?
    /**
     * The slots that this can use.
     * */
    val affectedSlots: Range
    /**
     * when set, it's the item that we filter on this side, or the one we return.
     * */
    val item: Stack?
}
