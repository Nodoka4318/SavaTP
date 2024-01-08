package net.kankantari.savatp.io

import com.google.gson.Gson
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*

class LocationData {
    var X: Double = 0.0;
    var Y: Double = 0.0;
    var Z: Double = 0.0;
    var Yaw: Float = 0.0f;
    var Pitch: Float = 0.0f;
    var World: String = "";
    var Name: String = "";
    var CreatorUUID: String = "";

    constructor(x: Double, y: Double, z: Double, yaw: Float, pitch: Float, world: String, name: String, creator: String) {
        X = x;
        Y = y;
        Z = z;
        Yaw = yaw;
        Pitch = pitch;
        World = world;
        Name = name;
        CreatorUUID = creator;
    }

    constructor() {
        X = 0.0;
        Y = 0.0;
        Z = 0.0;
        Yaw = 0.0f;
        Pitch = 0.0f;
        World = "";
        Name = "";
        CreatorUUID = "";
    }

    fun formatJson(): String {
        val gson = Gson();
        return gson.toJson(this);
    }

    fun getLocation(): Location {
        var world = Bukkit.getWorld(World);
        return Location(world, X, Y, Z, Yaw, Pitch);
    }

    fun setLocation(location: Location) {
        X = location.x;
        Y = location.y;
        Z = location.z;
        Yaw = location.yaw;
        Pitch = location.pitch;
        World = location.world.name;
    }

    fun getCreatorName(): String {
        if (CreatorUUID == "") {
            return "*";
        }

        return Bukkit.getOfflinePlayer(UUID.fromString(CreatorUUID)).name;
    }

    companion object {
        fun parseJson(json: String): LocationData {
            val gson = Gson();
            return gson.fromJson(json, LocationData::class.java);
        }
    }
}