package net.benwoodworth.fastcraft.bukkit.recipe

import net.benwoodworth.fastcraft.bukkit.item.BukkitFcItem.Companion.itemStack
import net.benwoodworth.fastcraft.platform.item.FcItem
import org.bukkit.inventory.RecipeChoice
import java.util.*

open class BukkitFcIngredient_1_13_1_R01 protected constructor(
    final override val slotIndex: Int,
) : BukkitFcIngredient {
    private lateinit var recipeChoice: RecipeChoice

    init {
        require(slotIndex in 0..8)
    }

    constructor(slotIndex: Int, recipeChoice: RecipeChoice) : this(slotIndex) {
        this.recipeChoice = recipeChoice
    }

    override fun matches(item: FcItem): Boolean {
        return recipeChoice.test(item.itemStack)
    }

    override fun equals(other: Any?): Boolean {
        return other is BukkitFcIngredient_1_13_1_R01 &&
                slotIndex == other.slotIndex &&
                recipeChoice == other.recipeChoice
    }

    override fun hashCode(): Int {
        return Objects.hash(slotIndex, recipeChoice)
    }
}