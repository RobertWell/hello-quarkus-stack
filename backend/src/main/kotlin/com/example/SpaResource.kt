package com.example

import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.io.InputStream

@Path("/")
@PermitAll
class SpaResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    @PermitAll
    fun homepage(): Response {
        // Homepage is accessible without authentication
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
    @PermitAll
    fun introduction(): Response {
        // Introduction page is accessible without authentication
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
    @PermitAll
    fun spa(@PathParam("path") path: String): Response {
        // Don't intercept API routes and actuator routes
        if (path.startsWith("api/") || path.startsWith("actuator/") || path.startsWith("q/") || path.startsWith("test")) {
            return Response.status(404).build()
        }
        
        // Serve index.html for all SPA routing (no authentication required)
        val indexHtml: InputStream? = javaClass.getResourceAsStream("/META-INF/resources/index.html")
        return if (indexHtml != null) {
            Response.ok(indexHtml).build()
        } else {
            Response.status(404).build()
        }
    }
}