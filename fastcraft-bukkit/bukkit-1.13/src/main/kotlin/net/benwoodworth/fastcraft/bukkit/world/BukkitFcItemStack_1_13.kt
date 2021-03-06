package net.benwoodworth.fastcraft.bukkit.world

import net.benwoodworth.fastcraft.platform.text.FcText
import net.benwoodworth.fastcraft.platform.world.FcItem
import net.benwoodworth.fastcraft.platform.world.FcItemStack
import org.bukkit.Server
import org.bukkit.inventory.ItemStack
import javax.inject.Inject
import javax.inject.Singleton

class BukkitFcItemStack_1_13(
    itemStack: ItemStack,
    items: FcItem.Factory,
    textFactory: FcText.Factory,
) : BukkitFcItemStack_1_7(
    bukkitItemStack = itemStack,
    items = items,
    textFactory = textFactory
) {
    override val type: FcItem
        get() = items.fromMaterial(bukkitItemStack.type)

    @Singleton
    open class Factory @Inject constructor(
        items: FcItem.Factory,
        textFactory: FcText.Factory,
        server: Server,
    ) : BukkitFcItemStack_1_7.Factory(
        items = items,
        textFactory = textFactory,
        server = server,
    ) {
        override fun create(itemStack: ItemStack): FcItemStack {
            return BukkitFcItemStack_1_13(
                itemStack = itemStack,
                items = items,
                textFactory = textFactory
            )
        }
    }
}
