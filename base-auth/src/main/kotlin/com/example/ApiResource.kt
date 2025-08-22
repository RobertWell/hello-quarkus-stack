package com.example

import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/api")
@RolesAllowed("api-user")
class ApiResource {

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    fun getStatus(): Map<String, Any> {
        return mapOf(
            "status" to "OK",
            "authenticated" to true,
            "service" to "Protected API",
            "timestamp" to System.currentTimeMillis()
        )
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    fun getInfo(): Map<String, Any> {
        return mapOf(
            "application" to "Quarkus Basic Auth Server",
            "version" to "1.0.0",
            "description" to "A Quarkus server with basic authentication",
            "endpoints" to listOf(
                "/api/introduction",
                "/api/status", 
                "/api/info"
            )
        )
    }
}