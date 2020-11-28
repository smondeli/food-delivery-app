package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/consumers")
public class ConsumerResource {
    @GET
    @Path("/validate")
    public Response validateConsumer(){
        return Response.ok("Validated consumer state.").build();
    }
}
