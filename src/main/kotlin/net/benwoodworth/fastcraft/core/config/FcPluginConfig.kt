package net.benwoodworth.fastcraft.core.config

import net.benwoodworth.fastcraft.dependencies.config.Config

/**
 * A wrapper for the plugin config.
 */
class FcPluginConfig(
        override var config: Config
) : ConfigWrapper {

    /**
     * The localization FastCraft will use.
     */
    var language: String
        get() = config.getString("language")!!
        set(value) = config.setString("language", value)

    /**
     * ID's for recipes that should be disabled.
     */
    var hiddenRecipes: List<String>
        get() = config.getStringList("disabled-recipes")!!
        set(value) = config.setStringList("disabled-recipes", value)
}