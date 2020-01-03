package net.benwoodworth.fastcraft.crafting.view.buttons

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import net.benwoodworth.fastcraft.crafting.model.FastCraftRecipe
import net.benwoodworth.fastcraft.platform.gui.FcGuiButton
import net.benwoodworth.fastcraft.platform.item.FcItemTypes
import net.benwoodworth.fastcraft.platform.text.FcTextColors
import net.benwoodworth.fastcraft.platform.text.FcTextFactory

@AutoFactory
class RecipeButtonView(
    private val button: FcGuiButton,
    @Provided private val itemTypes: FcItemTypes,
    @Provided private val textFactory: FcTextFactory,
    @Provided private val textColors: FcTextColors
) {
    var fastCraftRecipe: FastCraftRecipe? = null

    lateinit var onCraft: (recipe: FastCraftRecipe, dropResults: Boolean) -> Unit

    init {
        button.onClick = { event ->
            fastCraftRecipe?.let {
                onCraft(it, event.isDropClick)
            }
        }
    }

    fun update() {
        button.apply {
            button.clear()

            fastCraftRecipe?.preparedRecipe?.let { recipe ->
                val previewItem = recipe.resultsPreview.first()
                copyItem(previewItem)

                val newDescription = mutableListOf(
                    textFactory.createFcText("Ingredients:")
                )

                if (previewItem.lore.any()) {
                    newDescription += textFactory.createFcText()
                    newDescription += previewItem.lore
                }

                description = newDescription
            }
        }
    }
}