package resources;

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
    public Response createOrder(){
        StringBuilder response = new StringBuilder();
        response.append("-> Received Order request.").append("\n");
        response.append("-> ")
                .append(ClientBuilder.newClient()
                .target(validateConsumerURL)
                .request().get().readEntity(String.class))
                .append("\n");
        response.append("-> ")
                .append(ClientBuilder.newClient()
                .target(ticketToCookFoodUrl)
                .request().get().readEntity(String.class))
                .append("\n");
        response.append("-> ")
                .append(ClientBuilder.newClient()
                .target(validateCreditCardUrl)
                .request().get().readEntity(String.class))
                .append("\n");
        response.append("-> Completed processing order.\n");
        return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
    }
    @GET
    @Path("/restaurants/menu")
    public Response getrestaurantMenu(){
        return ClientBuilder.newClient()
                .target(restaurantMenuUrl)
                .request().get();
    }
}
