package me.garfiez.cowsay.bungee.repository.sql

import me.garfiez.cowsay.bungee.repository.UserRepository
import me.garfiez.cowsay.sql.SqlDatabaseService

class SqlUserRepository(
    private val databaseService: SqlDatabaseService
) : UserRepository {

    init {
        databaseService.execute(
            """
                create table if not exists cowsay (
                    username varchar(16) primary key, 
                    lastSay text, 
                    sayCount int
                )
            """.trimIndent()
        )
    }

    override fun updateSay(player: String, text: String) {
        val exists = databaseService.execute(
            "select 1 from cowsay where username = ?", player,
            handler = { rs ->
                rs.next()
            })

        if (exists) {
            databaseService.execute(
                "update cowsay set lastSay = ?, sayCount = sayCount + 1 where username = ?",
                text, player
            )
        } else {
            databaseService.execute(
                "insert into cowsay (username, lastSay, sayCount) values (?, ?, 1)",
                player, text
            )
        }
    }

    override fun getSayCount(player: String): Int {
        return databaseService.execute(
            "select sayCount from cowsay where username = ?", player,
            handler = { rs ->
                if (rs.next()) {
                    rs.getInt("sayCount")
                } else {
                    0
                }
            })
    }
}