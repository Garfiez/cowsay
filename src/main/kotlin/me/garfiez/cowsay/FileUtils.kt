package me.garfiez.cowsay

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.function.Supplier

object FileUtils {

    fun saveDefaultResource(dataFolder: File, name: String, resourceProvider: Supplier<InputStream>) {
        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }

        val file = File(dataFolder, name)

        if (!file.exists()) {
            FileOutputStream(file).use { out ->
                resourceProvider.get().use { it.transferTo(out) }
            }
        }
    }
}