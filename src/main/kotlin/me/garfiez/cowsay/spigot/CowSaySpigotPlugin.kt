package me.garfiez.cowsay.spigot

import me.garfiez.cowsay.Channel
import me.garfiez.cowsay.FileUtils
import me.garfiez.cowsay.spigot.config.Config
import me.garfiez.cowsay.spigot.config.ConfigLoader
import me.garfiez.cowsay.spigot.config.impl.YamlConfigLoader
import me.garfiez.cowsay.spigot.entity.CustomEntityTypes
import me.garfiez.cowsay.spigot.message.MessageConsumer
import me.garfiez.cowsay.spigot.service.impl.CowCircleMovementService
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class CowSaySpigotPlugin : JavaPlugin() {

    override fun onEnable() {
        saveDefaultResources()

        CustomEntityTypes.registerAll()

        val config = loadConfig()
        registerMessageListener(config)
    }

    override fun onDisable() {
        unregisterMessageListener()
        CustomEntityTypes.unregisterAll()
    }

    private fun registerMessageListener(config: Config) {
        val cowMovementService = CowCircleMovementService(this, config)
        val messageConsumer = MessageConsumer(cowMovementService)

        server.messenger.registerIncomingPluginChannel(this, Channel.NAME, messageConsumer)
    }

    private fun unregisterMessageListener() {
        server.messenger.unregisterIncomingPluginChannel(this, Channel.NAME)
    }

    private fun saveDefaultResources() {
        FileUtils.saveDefaultResource(dataFolder, "config.yml") { getResource("spigot/config.yml") }
    }

    private fun loadConfig(): Config {
        val configLoader: ConfigLoader = YamlConfigLoader(config)
        return configLoader.load()
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isNotEmpty() && args[0].equals("reload", true)) {
            //TODO remove all movement tasks + kill entities

            unregisterMessageListener()

            reloadConfig()
            val config = loadConfig()

            registerMessageListener(config)
        } else if (args[0].equals("reload", true)) {
            sender.sendMessage("/cowsay reload - перезагрузить плагин")
        }

        return true
    }
}
