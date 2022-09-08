package me.garfiez.cowsay.spigot.service

import me.garfiez.cowsay.spigot.data.SayData
import org.bukkit.entity.Player

interface CowMovementService {

    fun runFor(player: Player, sayData: SayData)
}