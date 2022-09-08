package me.garfiez.cowsay.bungee.repository

interface UserRepository {

    fun updateSay(player: String, text: String)

    fun getSayCount(player: String): Int
}