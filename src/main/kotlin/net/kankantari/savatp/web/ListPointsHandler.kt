package net.kankantari.savatp.web

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import net.kankantari.savatp.SavaTP
import java.io.OutputStream
import java.net.URI


class ListPointsHandler : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        val requestURI: URI? = exchange.getRequestURI();
        SavaTP.log("Request accepted ; ${requestURI.toString()}")
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", SavaTP.savaConfig.cors);

        val response = SavaTP.locationDataSet.formatJson();
        exchange.sendResponseHeaders(200, response.toByteArray().size.toLong());

        val os: OutputStream = exchange.getResponseBody()
        os.write(response.toByteArray())
        os.close()
    }
}