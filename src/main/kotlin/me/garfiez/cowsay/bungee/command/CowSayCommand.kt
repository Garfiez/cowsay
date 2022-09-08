package me.garfiez.cowsay.bungee.command

import me.garfiez.cowsay.bungee.service.CowSayService
import me.garfiez.cowsay.bungee.message.Messages
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command

class CowSayCommand(
    private val messages: Messages,
    private val cowSayService: CowSayService
) : Command("cowsay") {

    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (args.isEmpty()) {
            sender.sendMessage(messages["command_usage"])
            return
        }

        if (sender !is ProxiedPlayer) {
            sender.sendMessage(messages["command_is_only_for_players"])
            return
        }

        val text = args.joinToString(" ")
        cowSayService.cowSay(sender, text)
    }
}