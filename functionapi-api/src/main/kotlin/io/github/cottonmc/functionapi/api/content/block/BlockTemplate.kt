package io.github.cottonmc.functionapi.api.content.block

import io.github.cottonmc.functionapi.api.content.block.enums.BlockRenderLayer
import io.github.cottonmc.functionapi.api.content.block.enums.BlockSoundGroup
import io.github.cottonmc.functionapi.api.content.block.enums.BlockType
import io.github.cottonmc.functionapi.api.content.block.enums.Material
import io.github.cottonmc.functionapi.api.content.item.enums.Tools
import io.github.cottonmc.functionapi.api.content.CommandData
import io.github.cottonmc.functionapi.util.annotation.*
import java.lang.UnsupportedOperationException

@Name("blocksettings")
@Context("block-settings")
@Description("""
Sets the default values for this block.
""")
interface BlockTemplate : CommandData {

    @Name("entity_name", displayName = "Entity Name", valueName = "Full name of the class that should be the block entity of this block.")
    var entityName: String
        @Name("entity_name", displayName = "Entity Name", valueName = "Full name of the class that should be the block entity of this block.")
        @ArgumentSetter set
        @ArgumentGetter get


    @Name("block_name", displayName = "Block Name", valueName = "Full name of the class that should be used to create the block.")
    var blockName: String
        @Name("block_name", displayName = "Block Name", valueName = "Full name of the class that should be used to create the block.")
        @ArgumentSetter set
        @ArgumentGetter get


    @Name("hardness", valueName = "The higher the number, the harder it is to break the block", defaultValue = "1")
    var hardness: Float
        @Name("hardness", valueName = "The higher the number, the harder it is to break the block", defaultValue = "1")
        @ArgumentSetter set
        @ArgumentGetter get

    @Name("resistance", valueName = "The higher the number, the more resistant it is to explosions", defaultValue = "0")
    var resistance: Float
        @ArgumentSetter set
        @ArgumentGetter get


    @Name("create item", displayName = "Add an item", valueName = "when set to false, the block will have no item form. Useful for technical blocks", defaultValue = "true")
    var hasItem: Boolean
        @Name("resistance", valueName = "The higher the number, the more resistant it is to explosions", defaultValue = "0")
        @ArgumentSetter set
        @ArgumentGetter get


    var ticksRandomly: Boolean

        @Description(
                """when set to true, the block will receive random ticks.
Compatible with the random tick event of function api!""")
        @Name("enablerandomtick", displayName = "Enable Random ticks", defaultValue = "true")
        @ArgumentSetter set
        @ArgumentGetter get


    @ArgumentSetter(false)
    @Description("""create a new state based on a range of numbers""")
    @Name("newnumberstate")
    @Throws(UnsupportedOperationException::class)
    fun createIntProperty(@Name("state name") name: String, @Name("max value (counts up from 0)") value: Int)

    @ArgumentSetter(false)
    @Description("""create a new state based on a true/false value""")
    @Name("new_booleanstate")
    @Throws(UnsupportedOperationException::class)
    fun createBooleanProperty(@Name("state name") name: String)

    @ArgumentSetter(false)
    @Description("""create a new custom state based on a set of words""")
    @Name("add_to_customstate")
    @Throws(UnsupportedOperationException::class)
    fun addToStringProperty(@Name("the name of the state we create or modify") name: String, @Name("the new value we add to the state") value: String)

    @ArgumentGetter
    fun getPropertyCommands(): Array<String>

    var canSpawnMobs: Boolean
        @Name("can spawnmobs", valueName = "when set to false, it will disable mobspawning on this block", defaultValue = "true")
        @ArgumentSetter set
        @ArgumentGetter get


    fun getStates(): Array<BlockState<*>>

    var baseType: BlockType
        @Description("The block will act like the selected base, and those states will be added to it by default.")
        @Name("can spawnmobs", valueName = "when set to false, it will disable mobspawning on this block", defaultValue = "true")
        @ArgumentSetter set
        @ArgumentGetter get


    var material: Material

        @Description("Sets the material of the block.")
        @Name("material")
        @ArgumentSetter set
        @ArgumentGetter get

    var lightLevel: Int
        @Name("can spawnmobs", valueName = "when set to false, it will disable mobspawning on this block", defaultValue = "true")
        @ArgumentSetter set
        @ArgumentGetter get

    var isCollidable: Boolean
        @Name("can spawnmobs", valueName = "when set to false, it will disable mobspawning on this block", defaultValue = "true")
        @ArgumentSetter set
        @ArgumentGetter get

    var soundGroup: BlockSoundGroup
        @Description("The type of the sound that the block will make when stepped on, or broken")
        @Name("sound group")
        @ArgumentSetter set
        @ArgumentGetter get


    var tool: Tools
        @Description("the proper tool to break this block. On fabric, use the included tool tags instead.")
        @Name("proper tool")
        @ArgumentSetter set
        @ArgumentGetter get

    var miningLevel: Int
        @Name("mining level", valueName = "sets what tool level should be able to mine this block. (0 is wood, 4 is diamond)", defaultValue = "0")
        @ArgumentSetter set
        @ArgumentGetter get


    var isAir: Boolean
        @Name("make air", valueName = "if set to true, than the block will be considered an air block.", defaultValue = "false")
        @ArgumentSetter set
        @ArgumentGetter get


    var isInvisible: Boolean
        @Name("make invisible", valueName = "setting it to true makes the block invisible, like a barrier block.", defaultValue = "false")
        @ArgumentSetter set
        @ArgumentGetter get

    var isWaterlogged: Boolean
        @Name("make waterlogged", valueName = "setting it to true makes the block waterlogged. Adding a boolean state with the name 'waterlogged' does not have the same effect!", defaultValue = "false")
        @ArgumentSetter set
        @ArgumentGetter get

    var renderLayer: BlockRenderLayer
        @Name("setrenderlayer", defaultValue = "solid")
        @Description("This value controls the way the block is rendered (leaves are cutout mipped). Does not exist above 1.14.")
        @ArgumentSetter set
        @ArgumentGetter get

}