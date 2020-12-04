package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/restaurants")
public class RestaurantResource {
    @GET
    @Path("/menu")
    public Response getrRstaurantMenu(){
        return Response.ok("Received menu from restaurant.\n", MediaType.APPLICATION_JSON).build();
    }
}
