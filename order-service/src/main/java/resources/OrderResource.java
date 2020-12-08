package resources;

import auth.BasicUser;
import auth.OauthUser;
import com.github.mtakaki.dropwizard.circuitbreaker.jersey.CircuitBreaker;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Path("/")
public class OrderResource {
    private final String validateConsumerURL = "http://localhost:8081/api/consumers/validate";
    private final String ticketToCookFoodUrl = "http://localhost:8082/api/tickets";
    private final String validateCreditCardUrl = "http://localhost:8083/api/accounts/validate";
    private final String restaurantMenuUrl = "http://localhost:8084/api/restaurants/menu";
    private Client client = ClientBuilder.newClient();

    @GET
    @Path("/orders")
    @CircuitBreaker
    public Response createOrder(@Auth OauthUser user){
        StringBuilder response = new StringBuilder();
        response.append("-> Received Order request from ").append(user.getName()).append("\n");
        invokeAPI(response, validateConsumerURL);
        invokeAPI(response, ticketToCookFoodUrl);
        invokeAPI(response, validateCreditCardUrl);
        response.append("-> Completed processing order.\n");
        return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/orders/async")
    @CircuitBreaker
    public Response createAsyncOrder(@Auth OauthUser user) throws InterruptedException {
        StringBuilder response = new StringBuilder();
        response.append("-> Received Order request from ").append(user.getName()).append("\n");
        CountDownLatch countDownLatch = new CountDownLatch(3);
        invokeAsyncAPI(countDownLatch, response, validateConsumerURL);
        invokeAsyncAPI(countDownLatch, response, ticketToCookFoodUrl);
        invokeAsyncAPI(countDownLatch, response, validateCreditCardUrl);
        response.append("-> Completed processing order.\n");
        if (!countDownLatch.await(10, TimeUnit.SECONDS)) {
            System.out.println("Some requests didn't complete within the timeout");
        }
        return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
    }

    private void invokeAsyncAPI(CountDownLatch countDownLatch, StringBuilder response, String url) {
        client.target(url).request().rx().get().toCompletableFuture()
                .whenComplete((result, e) ->{
                    response.append("-> ")
                            .append(result.readEntity(String.class))
                            .append("\n");
                    countDownLatch.countDown();
                }
                );
    }

    private void invokeAPI(StringBuilder response, String url) {
        response.append("-> ")
                .append(client.target(url)
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
