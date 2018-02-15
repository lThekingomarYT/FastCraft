package net.benwoodworth.fastcraft.implementations.sponge.api.gui

import net.benwoodworth.fastcraft.api.gui.Gui
import net.benwoodworth.fastcraft.api.gui.GuiAbstract
import net.benwoodworth.fastcraft.api.gui.layout.GuiLayout
import net.benwoodworth.fastcraft.dependencies.player.Player
import net.benwoodworth.fastcraft.implementations.sponge.SpongeFastCraft
import net.benwoodworth.fastcraft.implementations.sponge.item.SpongeItem
import net.benwoodworth.fastcraft.implementations.sponge.player.SpongePlayer
import net.benwoodworth.fastcraft.implementations.sponge.text.SpongeText
import org.spongepowered.api.Sponge
import org.spongepowered.api.item.inventory.Carrier
import org.spongepowered.api.item.inventory.Inventory
import org.spongepowered.api.item.inventory.ItemStack
import org.spongepowered.api.item.inventory.property.InventoryTitle
import org.spongepowered.api.item.inventory.type.CarriedInventory
import org.spongepowered.api.item.inventory.type.GridInventory
import org.spongepowered.api.item.inventory.type.Inventory2D
import org.spongepowered.api.text.Text as Sponge_Text

/**
 * Sponge implementation of [Gui].
 */
abstract class SpongeGui<out TInv : Inventory>(
        plugin: Any,
        invProvider: (SpongeGui<TInv>) -> TInv
) : GuiAbstract(), Carrier {

    private companion object {
        var registeredListeners = false
    }

    init {
        if (!registeredListeners) {
            Sponge.getEventManager().registerListeners(plugin, SpongeGuiListeners())
            registeredListeners = true
        }
    }

    @Suppress("LeakingThis")
    private val inventory: TInv = invProvider(this).also {
        require(it is CarriedInventory<*>)
    }

    override fun getInventory() = inventory

    override val title get() = inventory.archetype // TODO Correct?
            .getProperty(InventoryTitle::class.java)
            .get().value?.let { SpongeText(it) }

    override fun open(vararg players: Player) {
        for (player in players) {
            (player as SpongePlayer).base.openInventory(inventory)
        }
    }

    override fun getViewers(): List<Player> {
        return Sponge.getServer().onlinePlayers
                .filter { it.openInventory === inventory }
                .map(::SpongePlayer)
    }

    protected fun GuiLayout.getSpongeItem(x: Int, y: Int): ItemStack? {
        return (getItem(x, y)?.mutableCopy() as SpongeItem.Mutable?)?.base
    }

    class Chest(
            plugin: SpongeFastCraft,
            invProvider: (SpongeGui<GridInventory>) -> GridInventory
    ) : Gui.Chest, SpongeGui<GridInventory>(plugin, invProvider) {
        override val layout = addLayout(9, inventory.columns)
        override fun updateLayout() {
            for (x in 0 until inventory.columns) {
                for (y in 0 until inventory.rows) {
                    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    inventory.set(x, y, layout.getSpongeItem(x, y))
                }
            }
        }
    }

    class Dispenser(
            plugin: SpongeFastCraft,
            invProvider: (SpongeGui) -> CarriedInventory<SpongeGui>
    ) : Gui.Dispenser, SpongeGui(plugin, invProvider) {
        override val layout = addLayout(3, 3)
    }

    class Hopper(
            plugin: SpongeFastCraft,
            invProvider: (SpongeGui) -> CarriedInventory<SpongeGui>
    ) : Gui.Hopper, SpongeGui(plugin, invProvider) {
        override val layout = addLayout(5, 1)
    }
}