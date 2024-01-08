package net.kankantari.savatp.web

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import net.kankantari.savatp.SavaTP
import net.kankantari.savatp.io.ApiKeyData
import org.bukkit.Bukkit
import java.io.OutputStream
import java.net.URI

class DeletePointHandler : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        val requestURI: URI? = exchange.getRequestURI();
        SavaTP.log("Request accepted ; ${requestURI.toString()}")
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8")
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", SavaTP.savaConfig.cors);

        val response = "{\"status\": \"ng\"}"
        exchange.sendResponseHeaders(401, response.toByteArray().size.toLong());

        var key = "";
        val params = requestURI?.query?.split("?");

        for (i in 0..params!!.size - 1) {
            if (params[i].startsWith("key=")) {
                key = params[i].split("=")[1];
                val apiKeyData = SavaTP.apiKeyDataSet.findApiKeyData(key);
                if (apiKeyData == null) {
                    break;
                }

                if (apiKeyData.playerId != exchange.getRemoteAddress().getAddress().getHostAddress()) {
                    break;
                }

            }
        }

        if (key != "") {
            for (i in 0..params!!.size - 1) {
                if (params[i].startsWith("point=")) {
                    val name = params[i].split("=")[1];
                    SavaTP.locationDataSet.deleteLocation(name);

                    val playerName = SavaTP.apiKeyDataSet.findApiKeyData(key)?.getPlayerName();
                    Bukkit.getOnlinePlayers().forEach {
                        SavaTP.sendMessage(
                            it,
                            "Teleport point §6§l$name §fhas been deleted by §6§l${playerName}§f via web!"
                        );
                    }

                    val response = "{\"status\": \"ok\"}"
                    exchange.sendResponseHeaders(200, response.toByteArray().size.toLong());
                    break;
                }
            }
        }

        val os: OutputStream = exchange.getResponseBody()
        os.write(response.toByteArray())
        os.close()
    }
}