package com.example

import io.quarkus.security.Authenticated
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.io.InputStream

@Path("/")
class SpaResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    fun homepage(): Response {
        // Homepage requires authentication
        val indexHtml: InputStream? = javaClass.getResourceAsStream("/META-INF/resources/index.html")
        return if (indexHtml != null) {
            Response.ok(indexHtml).build()
        } else {
            Response.status(404).build()
        }
    }

    @GET
    @Path("/introduction")
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    fun introduction(): Response {
        // Introduction page requires authentication
        val indexHtml: InputStream? = javaClass.getResourceAsStream("/META-INF/resources/index.html")
        return if (indexHtml != null) {
            Response.ok(indexHtml).build()
        } else {
            Response.status(404).build()
        }
    }

    @GET
    @Path("/{path:.*}")
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    fun spa(@PathParam("path") path: String): Response {
        // Don't intercept API routes and actuator routes
        if (path.startsWith("api/") || path.startsWith("actuator/") || path.startsWith("q/") || path.startsWith("test")) {
            return Response.status(404).build()
        }
        
        // Serve index.html for authenticated SPA routing
        val indexHtml: InputStream? = javaClass.getResourceAsStream("/META-INF/resources/index.html")
        return if (indexHtml != null) {
            Response.ok(indexHtml).build()
        } else {
            Response.status(404).build()
        }
    }
}