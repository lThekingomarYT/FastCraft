package net.benwoodworth.fastcraft.dependencies.gui

import net.benwoodworth.fastcraft.dependencies.event.Listener
import net.benwoodworth.fastcraft.dependencies.item.Item

/**
 * A button in a GUI.
 */
interface GuiButton {

    /**
     * A listener for layout changes.
     */
    val changeListener: Listener<EventGuiLayoutChange>

    /**
     * A listener for button clicks.
     */
    val clickListener: Listener<EventGuiButtonClick>

    /**
     * The item representing this button.
     */
    val item: Item?
}