package com.example

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/api/hello")
class HelloResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun hello(): HelloResponse {
        return HelloResponse("Hello from Quarkus with Kotlin!")
    }
    
    data class HelloResponse(val message: String)
}