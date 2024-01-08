package net.kankantari.savatp.web

import com.sun.net.httpserver.HttpServer
import net.kankantari.savatp.SavaTP
import java.io.IOException
import java.net.InetSocketAddress


class ApiServer {
    fun start(port: Int) {
        val server = HttpServer.create(InetSocketAddress(port), 0)
        server.createContext(SavaTP.savaConfig.context, ApiHandler());
        server.executor = null
        server.start()

        SavaTP.log("API Server started on port $port")
    }
}