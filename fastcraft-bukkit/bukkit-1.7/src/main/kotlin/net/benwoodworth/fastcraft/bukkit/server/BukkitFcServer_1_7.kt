package net.benwoodworth.fastcraft.bukkit.server

import net.benwoodworth.fastcraft.platform.server.FcLogger
import org.bukkit.Server
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BukkitFcServer_1_7 @Inject constructor(
    val server: Server,
    logger: FcLogger,
) : BukkitFcServer {
    override val minecraftVersion: String =
        Regex("""MC:\s*([.\d]*)""")
            .find(server.version)
            ?.run { groupValues[1] }
            ?: "unknown".also { logger.error("Unable to determine Minecraft version from '${server.version}'. Defaulting to 'unknown'") }
}
