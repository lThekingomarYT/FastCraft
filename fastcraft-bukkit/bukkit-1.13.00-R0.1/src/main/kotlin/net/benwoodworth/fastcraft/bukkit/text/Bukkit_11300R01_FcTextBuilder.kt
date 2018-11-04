package net.benwoodworth.fastcraft.bukkit.text

import net.benwoodworth.fastcraft.platform.text.FcText
import net.benwoodworth.fastcraft.platform.text.FcTextBuilder
import net.benwoodworth.fastcraft.platform.text.FcTextColor
import net.benwoodworth.fastcraft.platform.text.FcTextColors

@Suppress("ClassName")
class Bukkit_11300R01_FcTextBuilder : FcTextBuilder {

    private var text: String = ""
    private var color: FcTextColor? = null
    private var bold: Boolean? = null
    private var italic: Boolean? = null
    private var underline: Boolean? = null
    private var strikethrough: Boolean? = null
    private var obfuscate: Boolean? = null
    private val extra = mutableListOf<FcText>()

    override fun text(text: String): FcTextBuilder {
        this.text = text
        return this
    }

    override fun color(color: FcTextColor): FcTextBuilder {
        this.color = color
        return this
    }

    override fun color(color: FcTextColors.() -> FcTextColor): FcTextBuilder {
        this.color(color(Bukkit_11300R01_FcTextColors))
        return this
    }

    override fun bold(bold: Boolean): FcTextBuilder {
        this.bold = bold
        return this
    }

    override fun italic(italic: Boolean): FcTextBuilder {
        this.italic = italic
        return this
    }

    override fun underline(underline: Boolean): FcTextBuilder {
        this.underline = underline
        return this
    }

    override fun strikethrough(strikethrough: Boolean): FcTextBuilder {
        this.strikethrough = strikethrough
        return this
    }

    override fun obfuscate(obfuscate: Boolean): FcTextBuilder {
        this.obfuscate = obfuscate
        return this
    }

    override fun extra(text: FcText): FcTextBuilder {
        extra.add(text)
        return this
    }

    override fun extra(text: (textBuilder: FcTextBuilder) -> FcText): FcTextBuilder {
        extra(text(Bukkit_11300R01_FcTextBuilder()))
        return this
    }

    override fun build(): Bukkit_11300R01_FcTextText {
        return Bukkit_11300R01_FcTextText(
            text = text,
            color = color,
            bold = bold,
            italic = italic,
            underline = underline,
            strikethrough = strikethrough,
            obfuscate = obfuscate,
            extra = extra.toList()
        )
    }
}