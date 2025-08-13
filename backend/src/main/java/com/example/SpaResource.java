package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;

@Path("/")
public class SpaResource {

    @GET
    @Path("/{path:.*}")
    @Produces(MediaType.TEXT_HTML)
    public Response spa(@PathParam("path") String path) {
        // Don't intercept API routes
        if (path.startsWith("api/")) {
            return Response.status(404).build();
        }
        
        // Serve index.html for all other routes (SPA routing)
        InputStream indexHtml = getClass().getResourceAsStream("/META-INF/resources/index.html");
        if (indexHtml != null) {
            return Response.ok(indexHtml).build();
        }
        return Response.status(404).build();
    }
}