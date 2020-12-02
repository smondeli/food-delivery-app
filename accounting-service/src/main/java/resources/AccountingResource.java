package resources;

import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreaker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountingResource {
    @GET
    @Path("/validate")
    @CircuitBreaker
    public Response validateCreditCard(){
        invokeExceptionCode();
        return Response.ok("Validated credit card.", MediaType.APPLICATION_JSON).build();
    }

    private void invokeExceptionCode(){
        throw new WebApplicationException("Failed to connect to Accounting DB temporarily.");
    }
}
