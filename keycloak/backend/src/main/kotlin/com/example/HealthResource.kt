package com.example

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/actuator/health")
class HealthResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun health(): Response {
        return Response.ok(
            mapOf(
                "status" to "UP",
                "checks" to listOf(
                    mapOf(
                        "name" to "application",
                        "status" to "UP"
                    )
                )
            )
        ).build()
    }
}