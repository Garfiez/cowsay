package me.garfiez.cowsay.sql.impl

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.garfiez.cowsay.sql.SqlDatabaseConnectionProvider
import java.sql.Connection
import java.util.Properties

class HikariSqlDatabaseConnectionProvider(
    properties: Properties
) : SqlDatabaseConnectionProvider {

    private val hikariDataSource: HikariDataSource

    init {
        val hikariConfig = HikariConfig(properties)
        hikariDataSource = HikariDataSource(hikariConfig)
    }

    override fun getConnection(): Connection {
        return hikariDataSource.connection
    }

    override fun close() {
        hikariDataSource.close()
    }
}