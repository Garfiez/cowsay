package me.garfiez.cowsay.spigot.message

import com.google.common.io.ByteStreams
import me.garfiez.cowsay.Channel
import me.garfiez.cowsay.spigot.config.Config
import me.garfiez.cowsay.spigot.data.SayData
import me.garfiez.cowsay.spigot.service.CowMovementService
import net.minecraft.server.v1_12_R1.*
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.messaging.PluginMessageListener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

class MessageConsumer(
    private val cowMovementService: CowMovementService
) : PluginMessageListener {

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != Channel.NAME) {
            return
        }

        val data = ByteStreams.newDataInput(message)

        val lastSay = data.readUTF()
        val sayCount = data.readInt()

        cowMovementService.runFor(player, SayData(sayCount, lastSay))

//        val location = player.location
//        val radius = 6.0
//        val radPerSec = 1.5
//        val radPerTick = radPerSec / 4
//        val time = 20 * 4
//
//        val entity: CraftEntity = location.world.spawnEntity(location, EntityType.COW) as CraftEntity
//        val entityInsentient: EntityInsentient = entity.handle as EntityInsentient
//        entity.isCustomNameVisible = true
//        entity.customName = "$sayCount: $lastSay"
//        entityInsentient.isNoAI = false
////        val bool = entityInsentient.navigation.a(5.0, entity.location.y, 5.0, 0.5)
////        val navigation: Navigation = entityInsentient.navigation as Navigation
////        println(entityInsentient.onGround)
//
//        plugin.server.scheduler.scheduleSyncDelayedTask(plugin, {
//            println("onGround: ${entityInsentient.onGround}")
////            entity.velocity = Vector(1, 0, 1).normalize()
//            entityInsentient.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).value = 0.1
////            entityInsentient.move(EnumMoveType.SELF, 3.0, 0.0, 3.0)
//            val nav = entityInsentient.navigation.a(location.x + 3, location.y, location.z + 3, 1.0)
//            println("onGroundThen: ${entityInsentient.onGround} and $nav")
//        }, 5)
//
////        val nextPosition = Vector(5, 0, 5)
////        val distance = nextPosition.distance(location.toVector());
////        entity.velocity = nextPosition.subtract(location.toVector()).normalize().multiply(1)
////        entity.velocity = Vector(1, 0, 1)
////        entityInsentient.move(EnumMoveType.SELF, 5.0, 0.0, 5.0);
//
//        CowMovement(
//            0,
//            entityInsentient,
//            location,
//            radius,
//            radPerTick,
//            time,
//            player
//        )
////            .runTaskTimer(plugin, 0, 5)
    }

//    private class CowMovement2(
//
//    ) : BukkitRunnable() {
//
//        override fun run() {
//
//        }
//    }
//
//    private class CowMovement(
//        private var tick: Int = 0,
//        private val entity: EntityInsentient,
//        private val center: Location,
//        private val radius: Double,
//        private val radianPerTick: Double,
//        private val time: Int,
//        private val player: Player
//    ) : BukkitRunnable() {
//
//        override fun run() {
//            ++tick
//            val nextLoc = getLocationOnCircle(center, radius, radianPerTick * tick)
//
////            if (tick == 0) {
////                entity.teleportTo(nextLoc, false)
////            } else {
////                entity.move(EnumMoveType.SELF, nextLoc.x, nextLoc.y, nextLoc.z)
////            }
////            entity.teleportTo(nextLoc, false)
//            entity.navigation
//
//            if (tick == time) {
////                entity.world.createExplosion(entity, entity.locX, entity.locY, entity.locZ, 8F, false, false)
////                entity.die()
//                cancel()
//            }
//        }
//
//        private fun getLocationOnCircle(center: Location, radius: Double, radian: Double): Location {
//            val x: Double = center.x + radius * cos(radian)
//            val z: Double = center.z + radius * sin(radian)
//            val y: Double = center.y
//
//            val loc = Location(center.world, x, y, z)
//
//            val difference: Vector = center.toVector().clone().subtract(loc.toVector())
//            loc.direction = difference
//
//            return loc
//        }
//    }
}