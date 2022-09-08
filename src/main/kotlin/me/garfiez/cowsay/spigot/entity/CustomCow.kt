package me.garfiez.cowsay.spigot.entity

import net.minecraft.server.v1_12_R1.EnumMoveType
import net.minecraft.server.v1_12_R1.GenericAttributes
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.event.entity.CreatureSpawnEvent

class CustomCow(
    location: Location,
    displayName: String
) : net.minecraft.server.v1_12_R1.EntityCow((location.world as org.bukkit.craftbukkit.v1_12_R1.CraftWorld).handle) {

    init {
        setPosition(location.x, location.y, location.z)

        customNameVisible = true
        customName = displayName
        setInvulnerable(true)

        val world = location.world as org.bukkit.craftbukkit.v1_12_R1.CraftWorld
        world.addEntity<Entity>(this, CreatureSpawnEvent.SpawnReason.CUSTOM)
    }

    override fun r() {
    }

    fun setSpeed(speed: Double) {
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).value = speed
    }

    fun navigateTo(location: Location, speedMultiplier: Double) {
        navigation.a(
            location.x,
            location.y,
            location.z,
            speedMultiplier
        )
    }

    fun makeSound() {
        D()
    }
}