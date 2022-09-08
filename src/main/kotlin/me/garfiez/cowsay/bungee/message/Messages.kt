package me.garfiez.cowsay.bungee.message

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.config.Configuration

class Messages(
    private val file: Configuration
) {

    private val messages: MutableMap<String, BaseComponent> = HashMap()

    init {
        file.keys.forEach { name ->
            val text = file.getString(name)
            messages[name] = TextComponent(ChatColor.translateAlternateColorCodes('&', text))
        }
    }

    operator fun get(name: String): BaseComponent {
        return messages.getOrDefault(name, TextComponent(name))
    }
}