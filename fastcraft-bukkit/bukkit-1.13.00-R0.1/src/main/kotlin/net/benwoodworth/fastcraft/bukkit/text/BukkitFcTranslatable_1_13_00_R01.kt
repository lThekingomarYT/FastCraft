package net.benwoodworth.fastcraft.bukkit.text

import net.benwoodworth.fastcraft.platform.text.FcLocale

class BukkitFcTranslatable_1_13_00_R01(
    override val key: String
) : BukkitFcTranslatable {

    override fun translate(locale: FcLocale): String? {
        return "[$key]" // TODO Translate key
    }
}