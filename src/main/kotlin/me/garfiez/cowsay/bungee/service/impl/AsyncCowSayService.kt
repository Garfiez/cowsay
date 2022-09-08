package me.garfiez.cowsay.bungee.service.impl

import com.google.common.io.ByteStreams
import me.garfiez.cowsay.Channel
import me.garfiez.cowsay.bungee.repository.UserRepository
import me.garfiez.cowsay.bungee.service.CowSayService
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AsyncCowSayService(
    private val userRepository: UserRepository
) : CowSayService {

    private val executor: Executor = Executors.newSingleThreadExecutor()

    override fun cowSay(player: ProxiedPlayer, text: String) {
        executor.execute {
            userRepository.updateSay(player.name, text)
            val sayCount = userRepository.getSayCount(player.name)

            sendPluginMessage(player, text, sayCount)
        }
    }

    private fun sendPluginMessage(player: ProxiedPlayer, lastSay: String, sayCount: Int) {
        val message = ByteStreams.newDataOutput()
        message.writeUTF(lastSay)
        message.writeInt(sayCount)

        player.server.info.sendData(Channel.NAME, message.toByteArray())
    }
}