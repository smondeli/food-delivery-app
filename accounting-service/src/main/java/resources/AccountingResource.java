package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountingResource {
    @GET
    @Path("/validate")
    public Response validateCreditCard(){
        return Response.ok("Validated credit card.", MediaType.APPLICATION_JSON).build();
    }
}
