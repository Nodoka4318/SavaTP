package net.kankantari.savatp.web

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import net.kankantari.savatp.SavaTP
import java.io.IOException
import java.io.OutputStream
import java.net.URI


class ApiHandler : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        val requestURI: URI? = exchange.getRequestURI();
        SavaTP.log("Request accepted ; ${requestURI.toString()}")
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8")
        val response = SavaTP.locationDataSet.formatJson();

        val os: OutputStream = exchange.getResponseBody()
        os.write(response.toByteArray())
        os.close()
    }
}