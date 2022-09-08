package me.garfiez.cowsay.sql

import org.intellij.lang.annotations.Language
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.function.Function

class SqlDatabaseService(
    private val connectionProvider: SqlDatabaseConnectionProvider
) {

    fun execute(@Language("SQL") query: String, vararg params: Any) {
        connectionProvider.getConnection().use {
            val statement = it.prepareStatement(query)
            setParams(statement, params)
            statement.executeUpdate()
        }
    }

    fun <T> execute(@Language("SQL") query: String, vararg params: Any, handler: Function<ResultSet, T>): T {
        connectionProvider.getConnection().use {
            val statement = it.prepareStatement(query)
            setParams(statement, params)
            val rs = statement.executeQuery()
            return handler.apply(rs)
        }
    }

    private fun setParams(statement: PreparedStatement, params: Array<out Any>) {
        if (params.isNotEmpty()) {
            for ((index, param) in params.withIndex()) {
                statement.setObject(index + 1, param)
            }
        }
    }
}