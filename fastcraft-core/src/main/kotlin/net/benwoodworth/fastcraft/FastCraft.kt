package net.benwoodworth.fastcraft

import net.benwoodworth.fastcraft.crafting.FastCraftGui
import net.benwoodworth.fastcraft.platform.player.FcPlayerEvents
import net.benwoodworth.fastcraft.platform.player.FcPlayerOpenWorkbenchEvent
import net.benwoodworth.fastcraft.platform.server.FcLogger
import javax.inject.Inject

class FastCraft @Inject internal constructor(
    playerEventsListeners: FcPlayerEvents,
    logger: FcLogger,
    private val fastCraftGuiFactory: FastCraftGui.Factory,
) {
    init {
        Strings.load()
        playerEventsListeners.onPlayerOpenWorkbench += ::onPlayerOpenWorkbench

        logger.info("Note: Commands and configuration have not been implemented yet, and will be added in a future release.")
    }

    fun disable() {
    }

    private fun onPlayerOpenWorkbench(event: FcPlayerOpenWorkbenchEvent) {
        if (!event.player.hasPermission(Permissions.FASTCRAFT_USE)) {
            return
        }

        fastCraftGuiFactory
            .createFastCraftGui(event.player)
            .open()

        event.cancel()
    }
}
