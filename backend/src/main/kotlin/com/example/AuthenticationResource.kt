package com.example

import io.quarkus.security.identity.SecurityIdentity
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/auth")
class AuthenticationResource {

    @Inject
    lateinit var identity: SecurityIdentity

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    fun checkAuth(): Response {
        return if (identity.isAnonymous) {
            Response.status(401).entity(
                mapOf(
                    "authenticated" to false,
                    "loginUrl" to "/q/oidc/code-flow"
                )
            ).build()
        } else {
            Response.ok(
                mapOf(
                    "authenticated" to true,
                    "username" to identity.principal.name,
                    "roles" to identity.roles
                )
            ).build()
        }
    }

    @GET
    @Path("/login")
    fun login(): Response {
        // This will trigger OIDC authentication
        return Response.status(302)
            .header("Location", "/q/oidc/code-flow")
            .build()
    }
    
    @GET
    @Path("/logout")
    fun logout(): Response {
        return Response.status(302)
            .header("Location", "/q/oidc/logout")
            .build()
    }
}