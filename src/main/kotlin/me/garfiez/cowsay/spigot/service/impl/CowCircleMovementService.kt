package me.garfiez.cowsay.spigot.service.impl

import me.garfiez.cowsay.spigot.config.Config
import me.garfiez.cowsay.spigot.data.SayData
import me.garfiez.cowsay.spigot.entity.CustomCow
import me.garfiez.cowsay.spigot.service.CowMovementService
import net.minecraft.server.v1_12_R1.DamageSource
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class CowCircleMovementService(
    private val plugin: Plugin,
    private val config: Config
) : CowMovementService {

    //TODO limit movement speed
    private companion object {

        const val MOVEMENT_DELAY = 40L
        const val MOVEMENT_PERIOD = 4L
        const val TICKS_PER_SECOND = 20
        const val SPEED_MULTIPLIER = 0.0

        fun getLocationOnCircle(center: Location, radius: Double, radian: Double): Location {
            val x: Double = center.x + radius * cos(radian)
            val z: Double = center.z + radius * sin(radian)
            val y: Double = center.y

            val loc = Location(center.world, x, y, z)

            val difference: Vector = center.toVector().clone().subtract(loc.toVector())
            loc.direction = difference

            return loc
        }
    }

    override fun runFor(player: Player, sayData: SayData) {
        val spawnLocation = getLocationOnCircle(player.location, config.radius, 0.0)
        val displayName = "${sayData.sayCount}: ${sayData.lastSay}"

        val entity = CustomCow(spawnLocation, displayName)

        entity.setSpeed(config.speed / (TICKS_PER_SECOND / MOVEMENT_PERIOD))

//        val radianPerMovement: Double = ((config.speed / TICKS_PER_SECOND) * MOVEMENT_PERIOD) / config.radius

        CowMovementRunnable(
            entity,
            player.location,
            config.radius,
            config.speed,
            config.ttl
        ).andThen {
            player.world.createExplosion(entity.locX, entity.locY, entity.locZ, 5.0F, false, false)
            entity.die(DamageSource.MAGIC)
        }.runTaskTimer(plugin, MOVEMENT_DELAY, MOVEMENT_PERIOD)
    }

    private class CowMovementRunnable(
        private val entity: CustomCow,
        private val center: Location,
        private val radius: Double,
        speed: Double,
        time: Int
    ) : BukkitRunnable() {

        private var movementsCount: Long = 1
        private var totalMovementsCount: Long = time * (TICKS_PER_SECOND / MOVEMENT_PERIOD)
        private var actionAfter: Runnable? = null
        private val radianPerMovement = PI - 2 * acos(speed / ((TICKS_PER_SECOND / MOVEMENT_PERIOD) * 2 * radius))

        override fun run() {
            if (movementsCount >= totalMovementsCount) {
                cancel()
                actionAfter?.run()
                return
            }

            val nextLocation = getLocationOnCircle(center, radius, radianPerMovement * movementsCount)
            entity.makeSound()
            nextLocation.world.spawnParticle(Particle.REDSTONE, nextLocation.add(0.5, 0.5, 0.5), 1)
            entity.navigateTo(nextLocation, SPEED_MULTIPLIER)

            movementsCount++
        }

        fun andThen(runnable: Runnable): CowMovementRunnable {
            actionAfter = runnable
            return this
        }
    }

}