Prefab mod content registration command reference:


blocksettings:
--------------


Sets the default values for this block.


create a new custom state based on a set of words


`blocksettings addtocustomstate <the name of the state we create or modify> <the new value we add to the state>`


The block will act like the selected base, and those states will be added to it by default.


`blocksettings block_type directional`



`blocksettings block_type door`



`blocksettings block_type fence`



`blocksettings block_type normal`



`blocksettings block_type stairs`



`blocksettings block_type trapdoor`



`blocksettings block_type wall`



`blocksettings canspawnmobs <when set to false, it will disable mobspawning on this block>`



`blocksettings createitem <when set to false, the block will have no item form. Useful for technical blocks>`



`blocksettings enablecollosions <if set to false, the block will not collide with entities, so you can walk through it.>`


when set to true, the block will receive random ticks.
Compatible with the random tick event of function api!


`blocksettings enablerandomtick <arg0>`



`blocksettings hardness <The higher the number, the harder it is to break the block>`



`blocksettings lightlevel <Anything higher than 0 will make the block emit light, anything higher than 15 will be set to 15.>`



`blocksettings makeair <if set to true, than the block will be considered an air block.>`



`blocksettings makeinvisible <setting it to true makes the block invisible, like a barrier block.>`



`blocksettings makewaterlogged <setting it to true makes the block waterlogged. Adding a boolean state with the name 'waterlogged' does not have the same effect!>`


Sets the material of the block.


`blocksettings material air`



`blocksettings material anvil`



`blocksettings material bamboo`



`blocksettings material bamboo_sapling`



`blocksettings material barrier`



`blocksettings material bubble_column`



`blocksettings material cactus`



`blocksettings material cake`



`blocksettings material carpet`



`blocksettings material clay`



`blocksettings material cobweb`



`blocksettings material earth`



`blocksettings material egg`



`blocksettings material fire`



`blocksettings material glass`



`blocksettings material ice`



`blocksettings material lava`



`blocksettings material leaves`



`blocksettings material metal`



`blocksettings material organic`



`blocksettings material packed_ice`



`blocksettings material piston`



`blocksettings material plant`



`blocksettings material portal`



`blocksettings material pumpkin`



`blocksettings material redstone_lamp`



`blocksettings material replaceable_plant`



`blocksettings material sand`



`blocksettings material seagrass`



`blocksettings material shulker_box`



`blocksettings material snow`



`blocksettings material snow_block`



`blocksettings material sponge`



`blocksettings material stone`



`blocksettings material structure_void`



`blocksettings material underwater_plant`



`blocksettings material water`



`blocksettings material wood`



`blocksettings material wool`



`blocksettings mining_level <sets what tool level should be able to mine this block. (0 is wood, 4 is diamond)>`


create a new state based on a true/false value


`blocksettings newbooleanstate <state name>`


create a new state based on a range of numbers


`blocksettings newnumberstate <state name> <max value (counts up from 0)>`


the proper tool to break this block. On fabric, use the included tool tags instead.


`blocksettings propertool axes`



`blocksettings propertool hands`



`blocksettings propertool hoes`



`blocksettings propertool pickaxes`



`blocksettings propertool shovels`



`blocksettings propertool swords`



`blocksettings resistance <The higher the number, the more resistant it is to explosions>`


This value controls the way the block is rendered (leaves are cutout mipped). Does not exist above 1.14.


`blocksettings setrenderlayer cutout`



`blocksettings setrenderlayer cutout_mipped`



`blocksettings setrenderlayer solid`



`blocksettings setrenderlayer translucent`


The type of the sound that the block will make when stepped on, or broken


`blocksettings soundgroup anvil`



`blocksettings soundgroup bamboo`



`blocksettings soundgroup bamboo_sapling`



`blocksettings soundgroup coral`



`blocksettings soundgroup crop`



`blocksettings soundgroup glass`



`blocksettings soundgroup grass`



`blocksettings soundgroup gravel`



`blocksettings soundgroup ladder`



`blocksettings soundgroup lantern`



`blocksettings soundgroup metal`



`blocksettings soundgroup nether_wart`



`blocksettings soundgroup sand`



`blocksettings soundgroup scaffolding`



`blocksettings soundgroup slime`



`blocksettings soundgroup snow`



`blocksettings soundgroup stem`



`blocksettings soundgroup stone`



`blocksettings soundgroup sweet_berry_bush`



`blocksettings soundgroup wet_grass`



`blocksettings soundgroup wood`



`blocksettings soundgroup wool`



include:
--------

Includes other files into the script. For other mccontent files it's the same as the 'function' command


`include <id>`

