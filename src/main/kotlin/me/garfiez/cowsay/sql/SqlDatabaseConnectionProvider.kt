package me.garfiez.cowsay.sql

import java.sql.Connection

interface SqlDatabaseConnectionProvider {

    fun getConnection(): Connection

    fun close()
}