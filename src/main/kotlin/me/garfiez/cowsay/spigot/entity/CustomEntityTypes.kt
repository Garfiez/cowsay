package me.garfiez.cowsay.spigot.entity

import net.minecraft.server.v1_12_R1.Entity
import net.minecraft.server.v1_12_R1.EntityCow
import net.minecraft.server.v1_12_R1.EntityTypes
import net.minecraft.server.v1_12_R1.MinecraftKey

enum class CustomEntityTypes(
    private val customName: String,
    private val id: Int,
    private val nmsClass: Class<out Entity>,
    private val customClass: Class<out Entity>,
    private val key: MinecraftKey = MinecraftKey(customName),
    private val oldKey: MinecraftKey? = EntityTypes.b.b(nmsClass)
) {

    CUSTOM_COW("custom_cow", 92, EntityCow::class.java, CustomCow::class.java);

    companion object {

        fun registerAll() = values().forEach { it.register() }

        fun unregisterAll() = values().forEach { it.unregister() }
    }

    private fun register() {
        EntityTypes.d.add(key)
        EntityTypes.b.a(id, key, customClass)
    }

    private fun unregister() {
        EntityTypes.d.remove(key)
        EntityTypes.b.a(id, oldKey, nmsClass)
    }
}