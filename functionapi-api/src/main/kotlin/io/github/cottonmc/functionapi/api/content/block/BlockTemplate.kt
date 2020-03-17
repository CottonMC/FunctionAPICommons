package io.github.cottonmc.functionapi.api.content.block

import io.github.cottonmc.functionapi.api.content.block.enums.BlockRenderLayer
import io.github.cottonmc.functionapi.api.content.block.enums.BlockSoundGroup
import io.github.cottonmc.functionapi.api.content.block.enums.Material
import io.github.cottonmc.functionapi.api.content.item.enums.Tools
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.ArgumentSetter
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Context
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Description
import io.github.cottonmc.functionapi.util.commandbuilder.annotation.Name
import java.lang.UnsupportedOperationException

@Name("blocksettings")
@Context("block-settings")
@Description("""
Sets the default values for this block.
""")
interface BlockTemplate {
    var hardness:Float
    @ArgumentSetter
    @Name("hardness", valueName = "The higher the number, the harder it is to break the block",defaultValue = "1")
    set
    var resistance:Float
        @ArgumentSetter
        @Name("resistance", valueName = "The higher the number, the more resistant it is to explosions",defaultValue = "0")
        set

    var hasItem:Boolean
        @ArgumentSetter
        @Name("createitem", valueName = "when set to false, the block will have no item form. Useful for technical blocks",defaultValue = "true")
        set

    var ticksRandomly:Boolean
    @Description(
            """when set to true, the block will receive random ticks.
Compatible with the random tick event of function api!""")
        @ArgumentSetter @Name("enablerandomtick",defaultValue = "true") set

    @ArgumentSetter
    @Description("""create a new state based on a range of numbers""")
    @Name("newnumberstate")
    @Throws(UnsupportedOperationException::class)
    fun createIntProperty(@Name("state name") name: String, @Name("max value (counts up from 0)") value: Int)

    @ArgumentSetter
    @Description("""create a new state based on a true/false value""")
    @Name("newbooleanstate")
    @Throws(UnsupportedOperationException::class)
    fun createBooleanProperty(@Name("state name") name: String)

    @ArgumentSetter
    @Description("""create a new custom state based on a set of words""")
    @Name("addtocustomstate")
    @Throws(UnsupportedOperationException::class)
    fun addToStringProperty(@Name("the name of the state we create or modify") name: String, @Name("the new value we add to the state")value: String)

    var canSpawnMobs:Boolean
        @ArgumentSetter @Name("canspawnmobs", valueName = "when set to false, it will disable mobspawning on this block",defaultValue = "true") set

    fun getStates():Array<BlockState<*>>

    var isStairs:Boolean
        @Description("when set to true, the block will use all of the states of a stair block, and will connect with all other existing stairs")@ArgumentSetter @Name("isastairblock",defaultValue = "true") set

    var material: Material
        @ArgumentSetter
        @Name("material")
        @Description("Sets the material of the block.")
        set

    var lightLevel: Int
        @ArgumentSetter @Name("lightlevel", valueName = "Anything higher than 0 will make the block emit light, anything higher than 15 will be set to 15.",defaultValue = "0") set
    var isCollidable: Boolean
        @ArgumentSetter @Name("enablecollosions", valueName = "if set to false, the block will not collide with entities, so you can walk through it.",defaultValue = "true") set
    var soundGroup: BlockSoundGroup
        @ArgumentSetter @Name("soundgroup") @Description("The type of the sound that the block will make when stepped on, or broken") set
    var tool: Tools
        @ArgumentSetter @Name("propertool") @Description("the proper tool to break this block. On fabric, use the included tool tags instead.")set
    var miningLevel: Int
        @ArgumentSetter @Name("mining_level",valueName = "sets what tool level should be able to mine this block. (0 is wood, 4 is diamond)",defaultValue = "0") set
    var isAir: Boolean
        @ArgumentSetter @Name("makeair",valueName = "if set to true, than the block will be considered an air block.",defaultValue = "false") set
    var isInvisible: Boolean
        @ArgumentSetter @Name("makeinvisible",valueName = "setting it to true makes the block invisible, like a barrier block.",defaultValue = "false") set

    var isWaterlogged: Boolean
        @ArgumentSetter @Name("makewaterlogged",valueName = "setting it to true makes the block waterlogged. Adding a boolean state with the name 'waterlogged' does not have the same effect!",defaultValue = "false") set


    var renderLayer:BlockRenderLayer
      @Description("This value controls the way the block is rendered (leaves are cutout mipped). Does not exist above 1.14.")  @ArgumentSetter @Name("setrenderlayer",defaultValue = "solid") set
}