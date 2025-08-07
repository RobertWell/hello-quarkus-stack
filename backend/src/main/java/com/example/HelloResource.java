package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/hello")
public class HelloResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HelloResponse hello() {
        return new HelloResponse("Hello from Quarkus!");
    }
    
    public static class HelloResponse {
        public String message;
        
        public HelloResponse() {}
        
        public HelloResponse(String message) {
            this.message = message;
        }
    }
}