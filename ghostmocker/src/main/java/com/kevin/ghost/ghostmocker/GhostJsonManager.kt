package com.kevin.ghost.ghostmocker

import android.content.res.AssetManager
import android.util.Log
import com.kevin.ghost.ghostmocker.GhostConstants.LOG_TAG
import java.io.FileNotFoundException


internal class GhostJsonManager(
    private val assetManager: AssetManager
) {
    fun getJsonFromFile(fileName: String): String {
        val jsonFileName = if (fileName.contains(".json")) fileName else "$fileName.json"
        Log.d(LOG_TAG, "Final mock file name: $jsonFileName")

        return assetManager.runCatching {
            open(jsonFileName)
                .bufferedReader()
                .use { it.readText() }
        }.getOrElse {
            Log.d(LOG_TAG, "Error reading JSON file $jsonFileName. Check if it exists")
            throw FileNotFoundException("Error reading JSON file $jsonFileName. Check if it exists")
        }
    }
}