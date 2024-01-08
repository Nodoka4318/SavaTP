package net.kankantari.savatp.io

import org.bukkit.Bukkit
import java.util.*

class ApiKeyData {
    var apiKey: String = "";
    var playerId: String = "";

    fun getPlayerName(): String {
        return Bukkit.getOfflinePlayer(UUID.fromString(playerId)).name ?: ""
    }
}