package com.example

import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/api/introduction")
@RolesAllowed("api-user")
class IntroductionResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getIntroduction(): Map<String, Any> {
        return mapOf(
            "message" to "Welcome to the protected API!",
            "description" to "This endpoint requires basic authentication",
            "timestamp" to System.currentTimeMillis(),
            "service" to "Quarkus Basic Auth Server"
        )
    }
}