package me.garfiez.cowsay.bungee

import me.garfiez.cowsay.FileUtils
import me.garfiez.cowsay.bungee.command.CowSayCommand
import me.garfiez.cowsay.bungee.message.Messages
import me.garfiez.cowsay.bungee.repository.sql.SqlUserRepository
import me.garfiez.cowsay.bungee.service.impl.AsyncCowSayService
import me.garfiez.cowsay.sql.SqlDatabaseConnectionProvider
import me.garfiez.cowsay.sql.SqlDatabaseService
import me.garfiez.cowsay.sql.impl.HikariSqlDatabaseConnectionProvider
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.FileInputStream
import java.util.*

class CowSayBungeePlugin : Plugin() {

    private companion object {
        val yamlConfigurationProvider: ConfigurationProvider =
            ConfigurationProvider.getProvider(YamlConfiguration::class.java)

        const val databasePropertiesFileName: String = "database.properties"
        const val messagesFileName: String = "messages.yml"

        const val bungeeFolderPrefix: String = "bungee/"
    }

    private lateinit var sqlDatabaseConnectionProvider: SqlDatabaseConnectionProvider

    override fun onEnable() {
        saveDefaultResources()

        val properties = loadProperties(databasePropertiesFileName)
        val messagesConfiguration = loadConfiguration(messagesFileName)

        sqlDatabaseConnectionProvider = HikariSqlDatabaseConnectionProvider(properties)
        val databaseService = SqlDatabaseService(sqlDatabaseConnectionProvider)
        val userRepository = SqlUserRepository(databaseService)
        val cowSayService = AsyncCowSayService(userRepository)
        val messages = Messages(messagesConfiguration)

        val cowSayCommand = CowSayCommand(messages, cowSayService)
        proxy.pluginManager.registerCommand(this, cowSayCommand)
    }

    override fun onDisable() {
        sqlDatabaseConnectionProvider.close()
    }

    private fun saveDefaultResources() {
        saveDefaultResourceIfNotExists(databasePropertiesFileName)
        saveDefaultResourceIfNotExists(messagesFileName)
    }

    private fun saveDefaultResourceIfNotExists(name: String) {
        FileUtils.saveDefaultResource(dataFolder, name) { getResourceAsStream(bungeeFolderPrefix + name) }
    }

    private fun loadConfiguration(name: String): Configuration {
        val file = File(dataFolder, name)

        return yamlConfigurationProvider.load(file)
    }

    private fun loadProperties(name: String): Properties {
        val properties = Properties()
        val file = File(dataFolder, name)

        FileInputStream(file).use {
            properties.load(it)
        }

        return properties
    }
}