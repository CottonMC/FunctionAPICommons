package io.github.cottonmc.functionapi.api.content.item

interface AlteredLootTableItem {
    val tableID: String?
    fun hasAlteredLootTabe(): Boolean {
        return tableID != null
    }
}