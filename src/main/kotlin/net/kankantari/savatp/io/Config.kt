package net.kankantari.savatp.io

import com.google.gson.Gson
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardOpenOption

class Config {
    var port: Int;
    var context: String;

    constructor(port: Int, context: String) {
        this.port = port;
        this.context = context;
    }

    constructor() {
        this.port = 8080;
        this.context = "/savatp";
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
        private val folderDir: String = "./plugins/SavaTP/";
        private val fileDir: String = folderDir + "config.json";

        fun load(): Config {
            val folder = File(folderDir);
            val file = File(fileDir);

            if (!folder.exists()) {
                folder.mkdir();
            }

            if (!file.exists()) {
                val config = Config();
                config.save();
                return config;
            }

            val json = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).joinToString("\n");
            val gson = Gson();
            return gson.fromJson(json, Config::class.java);
        }
    }
}