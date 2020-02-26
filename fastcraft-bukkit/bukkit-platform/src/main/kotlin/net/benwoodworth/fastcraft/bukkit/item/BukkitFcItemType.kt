package net.benwoodworth.fastcraft.bukkit.item

import net.benwoodworth.fastcraft.platform.item.FcItemType
import org.bukkit.Material
import org.bukkit.material.MaterialData

interface BukkitFcItemType : FcItemType {
    val material: Material
    val materialData: Any?
}

val FcItemType.material: Material
    get() = (this as BukkitFcItemType).material

val FcItemType.materialData: Any?
    get() = (this as BukkitFcItemType).materialData