package me.garfiez.cowsay.bungee.service

import net.md_5.bungee.api.connection.ProxiedPlayer

interface CowSayService {

    fun cowSay(player: ProxiedPlayer, text: String)
}