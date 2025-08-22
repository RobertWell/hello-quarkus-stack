package com.example

import io.quarkus.oidc.IdToken
import io.quarkus.security.Authenticated
import io.quarkus.security.identity.SecurityIdentity
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.jwt.JsonWebToken

@Path("/api/user")
@Authenticated
class UserResource {

    @Inject
    lateinit var identity: SecurityIdentity

    @Inject
    @IdToken
    lateinit var idToken: JsonWebToken

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserInfo(): Map<String, Any?> {
        return mapOf(
            "username" to identity.principal.name,
            "roles" to identity.roles,
            "email" to (idToken.getClaim<Any>("email") ?: ""),
            "name" to (idToken.getClaim<Any>("name") ?: identity.principal.name)
        )
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    fun logout(): Map<String, String> {
        return mapOf(
            "message" to "Please clear your session and redirect to /q/oidc/logout"
        )
    }
}