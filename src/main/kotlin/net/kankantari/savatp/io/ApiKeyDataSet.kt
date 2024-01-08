package net.kankantari.savatp.io

import com.google.gson.Gson
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardOpenOption

class ApiKeyDataSet {
    var apiKeyDatas: MutableList<ApiKeyData> = mutableListOf<ApiKeyData>();

    fun getPlayerApiKeyData(playerId: String): ApiKeyData? {
        return apiKeyDatas.find { it.playerId == playerId };
    }

    fun findApiKeyData(key: String): ApiKeyData? {
        return apiKeyDatas.find { it.apiKey == key };
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
        private val fileDir = folderDir + "keys.json";

        fun parseJson(json: String): ApiKeyDataSet {
            val gson = Gson();
            return gson.fromJson(json, ApiKeyDataSet::class.java);
        }

        fun load(): ApiKeyDataSet {
            val file = File(fileDir);
            if (!file.exists()) {
                return ApiKeyDataSet();
            }

            val json = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).joinToString("\n");
            return parseJson(json);
        }

        fun generateApiKey(): String {
            return java.util.UUID.randomUUID().toString().replace("-", "");
        }
    }
}