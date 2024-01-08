package net.kankantari.savatp.io

import com.google.gson.Gson
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardOpenOption

class LocationDataSet {
    var Locations: MutableList<LocationData> = mutableListOf<LocationData>();

    constructor(locations: MutableList<LocationData>) {
        Locations = locations;
    }

    constructor() {
        Locations = mutableListOf<LocationData>();
    }

    fun getLocation(name: String): LocationData? {
        return Locations.find { it.Name == name };
    }

    fun addLocation(location: LocationData) {
        Locations.add(location);
    }

    fun deleteLocation(name: String): Boolean {
        val location = getLocation(name);
        if (location == null) {
            return false;
        }

        Locations.remove(location);
        return true;
    }

    fun formatJson(): String {
        val gson = Gson();
        return gson.toJson(this);
    }

    fun save() {
        val folder = File(folderDir);
        val file = File(fileDir);

        if (!folder.exists()) {
            folder.mkdir();
        }

        if (file.exists()) {
            file.delete();
        }

        Files.createFile(file.toPath());
        Files.write(
            file.toPath(),
            formatJson().split("\n"),
            StandardCharsets.UTF_8,
            StandardOpenOption.WRITE
        );
    }

    companion object {
        private val folderDir = "./plugins/SavaTP/";
        private val fileDir = folderDir + "locations.json";

        fun parseJson(json: String): LocationDataSet {
            val gson = Gson();
            return gson.fromJson(json, LocationDataSet::class.java);
        }

        fun load(): LocationDataSet {
            val file = File(fileDir);
            if (!file.exists()) {
                return LocationDataSet();
            }

            val json = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).joinToString("\n");
            return parseJson(json);
        }
    }
}