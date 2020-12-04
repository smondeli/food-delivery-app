package resources;

import auth.BasicUser;
import auth.OauthUser;
import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreaker;
import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreakerApplicationEventListener;
import io.dropwizard.auth.Auth;
import org.eclipse.jetty.server.Server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public class OrderResource {
    private final String validateConsumerURL = "http://localhost:8081/api/consumers/validate";
    private final String ticketToCookFoodUrl = "http://localhost:8082/api/tickets";
    private final String validateCreditCardUrl = "http://localhost:8083/api/accounts/validate";
    private final String restaurantMenuUrl = "http://localhost:8084/api/restaurants/menu";

    @GET
    @Path("/orders")
    @CircuitBreaker
    public Response createOrder(@Auth OauthUser user){
        CircuitBreakerApplicationEventListener abc;
        StringBuilder response = new StringBuilder();
        response.append("-> Received Order request from ").append(user.getName()).append("\n");
        invokeAPI(response, validateConsumerURL);
        invokeAPI(response, ticketToCookFoodUrl);
        invokeAPI(response, validateCreditCardUrl);
        response.append("-> Completed processing order.\n");
        return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
    }

    private void invokeAPI(StringBuilder response, String validateConsumerURL) {
        response.append("-> ")
                .append(ClientBuilder.newClient()
                        .target(validateConsumerURL)
                        .request().get().readEntity(String.class))
                .append("\n");
    }

    @GET
    @Path("/restaurants/menu")
    public Response getrestaurantMenu(@Auth BasicUser user){
        return ClientBuilder.newClient()
                .target(restaurantMenuUrl)
                .request().get();
    }
}
