package net.benwoodworth.fastcraft.bukkit.recipe

import net.benwoodworth.fastcraft.bukkit.item.createFcItem
import net.benwoodworth.fastcraft.bukkit.item.toItemStack
import net.benwoodworth.fastcraft.bukkit.player.player
import net.benwoodworth.fastcraft.platform.item.FcItem
import net.benwoodworth.fastcraft.platform.item.FcItemFactory
import net.benwoodworth.fastcraft.platform.player.FcPlayer
import net.benwoodworth.fastcraft.platform.recipe.FcCraftingRecipe
import net.benwoodworth.fastcraft.platform.recipe.FcCraftingRecipePrepared
import net.benwoodworth.fastcraft.platform.recipe.FcIngredient
import net.benwoodworth.fastcraft.util.CancellableResult
import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import java.util.*
import javax.inject.Inject

open class BukkitFcCraftingRecipe_1_7_5_R01(
    val recipe: Recipe,
    private val server: Server,
    private val preparedRecipeFactory: BukkitFcCraftingRecipePrepared_1_7_5_R01Factory,
    private val itemFactory: FcItemFactory,
    private val remnantProvider: IngredientRemnantProvider,
    private val inventoryViewFactory: PrepareCraftInventoryView_1_7_5_R01.Factory
) : BukkitFcCraftingRecipe {
    class Factory @Inject constructor(
        private val server: Server,
        private val preparedRecipeFactory: BukkitFcCraftingRecipePrepared_1_7_5_R01Factory,
        private val itemFactory: FcItemFactory,
        private val remnantProvider: IngredientRemnantProvider,
        private val inventoryViewFactory: PrepareCraftInventoryView_1_7_5_R01.Factory
    ) : BukkitFcCraftingRecipe.Factory {
        override fun create(recipe: Recipe): FcCraftingRecipe {
            return BukkitFcCraftingRecipe_1_7_5_R01(
                recipe = recipe,
                server = server,
                preparedRecipeFactory = preparedRecipeFactory,
                itemFactory = itemFactory,
                remnantProvider = remnantProvider,
                inventoryViewFactory = inventoryViewFactory
            )
        }
    }

    init {
        require(recipe is ShapedRecipe || recipe is ShapelessRecipe)
    }

    override val id: String by lazy {
        "#" + (hashCode().toLong() - Int.MIN_VALUE).toString(36)
    }

    override val ingredients: List<FcIngredient> by lazy {
        loadIngredients()
    }

    protected open fun loadIngredients(): List<FcIngredient> {
        return when (recipe) {
            is ShapedRecipe -> recipe.shape
                .mapIndexed { row, rowString ->
                    rowString
                        .mapIndexed { column, char ->
                            recipe.ingredientMap[char]?.let { ingredient ->
                                BukkitFcIngredient_1_7_5_R01(row * 3 + column, ingredient)
                            }
                        }
                        .filterNotNull()
                }
                .flatten()

            is ShapelessRecipe -> recipe.ingredientList
                .mapIndexed { i, ingredient ->
                    BukkitFcIngredient_1_7_5_R01(i, ingredient)
                }

            else -> throw IllegalStateException()
        }
    }

    override val group: String?
        get() = null

    override fun prepare(
        player: FcPlayer,
        ingredients: Map<FcIngredient, FcItem>
    ): CancellableResult<FcCraftingRecipePrepared> {
        // TODO Inventory owner
        val prepareView = inventoryViewFactory.create(player.player, null, recipe)
        val craftingGrid = prepareView.topInventory as CraftingInventory

        ingredients.forEach { (ingredient, item) ->
            craftingGrid.setItem(ingredient.slotIndex, item.toItemStack())
        }

        val validIngredients = ingredients.all { (ingredient, item) -> ingredient.matches(item) }
        if (validIngredients) {
            craftingGrid.result = recipe.result
        }

        val prepareEvent = PrepareItemCraftEvent(craftingGrid, prepareView, false)
        server.pluginManager.callEvent(prepareEvent)

        val resultItem = craftingGrid.result
        if (resultItem == null || resultItem.type == Material.AIR || resultItem.amount < 1) {
            return CancellableResult.Cancelled
        }

        val ingredientRemnants = ingredients.values
            .flatMap { remnantProvider.getRemnants(it.toItemStack()) }
            .map { itemFactory.createFcItem(it) }

        val resultsPreview = listOf(itemFactory.createFcItem(resultItem)) + ingredientRemnants

        return CancellableResult(
            preparedRecipeFactory.create(this, ingredients, ingredientRemnants, resultsPreview, prepareView)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other !is BukkitFcCraftingRecipe_1_7_5_R01) {
            return false
        }

        val otherRecipe = other.recipe

        return when {
            recipe is ShapedRecipe && otherRecipe is ShapedRecipe -> {
                recipe.ingredientMap == otherRecipe.ingredientMap &&
                        recipe.shape!!.contentEquals(otherRecipe.shape) &&
                        recipe.result == otherRecipe.result

            }
            recipe is ShapelessRecipe && otherRecipe is ShapelessRecipe -> {
                recipe.ingredientList == otherRecipe.ingredientList &&
                        recipe.result == otherRecipe.result
            }
            else -> {
                return false
            }
        }
    }

    override fun hashCode(): Int {
        return when (recipe) {
            is ShapedRecipe -> {
                Objects.hash(
                    recipe.result,
                    recipe.shape,
                    recipe.ingredientMap
                )
            }
            is ShapelessRecipe -> {
                Objects.hash(
                    recipe.result,
                    recipe.ingredientList
                )
            }
            else -> throw UnsupportedOperationException()
        }
    }
}