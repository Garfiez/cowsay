package me.garfiez.cowsay.spigot.message

import com.google.common.io.ByteStreams
import me.garfiez.cowsay.Channel
import me.garfiez.cowsay.spigot.data.SayData
import me.garfiez.cowsay.spigot.service.CowMovementService
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

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
    }
}