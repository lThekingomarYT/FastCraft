package net.benwoodworth.fastcraft.api.gui.layout

import net.benwoodworth.fastcraft.api.Listener
import net.benwoodworth.fastcraft.api.gui.GuiElement
import net.benwoodworth.fastcraft.api.gui.event.GuiEventClick
import net.benwoodworth.fastcraft.api.gui.event.GuiEventLayoutChange
import net.benwoodworth.fastcraft.dependencies.item.Item
import java.util.*

/**
 * Abstract implementation of [GuiLayout].
 */
abstract class GuiLayoutAbstract(
        x: Int,
        y: Int,
        width: Int,
        height: Int
) : GuiLayout {

    override val changeListener = Listener<GuiEventLayoutChange>()

    override var x = x
        set(value) {
            field = value
            changeListener.notifyHandlers(GuiEventLayoutChange())
        }

    override var y = y
        set(value) {
            field = value
            changeListener.notifyHandlers(GuiEventLayoutChange())
        }

    override var width = width
        set(value) {
            field = value
            changeListener.notifyHandlers(GuiEventLayoutChange())
        }

    override var height = height
        set(value) {
            field = value
            changeListener.notifyHandlers(GuiEventLayoutChange())
        }

    private val elements = LinkedList<GuiElement>()

    override fun addElement(element: GuiElement) {
        if (elements.any { it === element }) {
            elements.remove(element)
        } else {
            element.changeListener += changeListener::notifyHandlers
        }

        elements.addFirst(element)
        changeListener.notifyHandlers(GuiEventLayoutChange())
    }

    override fun removeElement(element: GuiElement) {
        elements.removeIf { it === element }
        element.changeListener -= changeListener::notifyHandlers
    }

    override fun getElement(x: Int, y: Int): GuiElement? {
        return elements.firstOrNull {
            (x in it.x..(it.x + it.width)) && (y in it.y..(it.y + it.height))
        }
    }

    override fun onClick(x: Int, y: Int, event: GuiEventClick) {
        getElement(x, y)?.let {
            it.onClick(x - it.x, y - it.y, event)
        }
    }

    override fun getItem(x: Int, y: Int): Item? {
        return getElement(x, y)?.let {
            it.getItem(x - it.x, y - it.y)
        }
    }
}
