package io.github.cottonmc.functionapi.api.content

interface AlteredLootTableItem {
    val tableID: String?
    fun hasAlteredLootTabe(): Boolean {
        return tableID != null
    }
}