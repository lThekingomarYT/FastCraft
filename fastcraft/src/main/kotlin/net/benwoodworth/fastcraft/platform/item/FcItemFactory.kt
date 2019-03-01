package net.benwoodworth.fastcraft.platform.item

import net.benwoodworth.fastcraft.platform.text.FcText

interface FcItemFactory {

    fun createFcItem(
        type: FcItemType,
        amount: Int = 1,
        displayName: FcText? = null,
        lore: List<FcText> = emptyList()
    ): FcItem

    fun FcItem.copy(
        type: FcItemType = this.type,
        amount: Int = this.amount,
        displayName: FcText? = this.displayName,
        lore: List<FcText> = this.lore
    ): FcItem
}