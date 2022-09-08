package me.garfiez.cowsay.spigot.config.impl

import me.garfiez.cowsay.spigot.config.Config
import me.garfiez.cowsay.spigot.config.ConfigLoader
import org.bukkit.configuration.ConfigurationSection

class YamlConfigLoader(
    private val section: ConfigurationSection
) : ConfigLoader {

    override fun load(): Config {
        return Config(
            section.getDouble("radius"),
            section.getDouble("speed"),
            section.getInt("ttl")
        )
    }
}