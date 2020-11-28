package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tickets")
public class KitchenResource {
    @GET
    public Response createTicket(){
        return Response.ok("Created ticket to cook customer order.", MediaType.APPLICATION_JSON).build();
    }
}
