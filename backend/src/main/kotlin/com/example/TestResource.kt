package com.example

import jakarta.annotation.security.PermitAll
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/test")
class TestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    fun test(): String {
        return "Test endpoint working without authentication"
    }
}