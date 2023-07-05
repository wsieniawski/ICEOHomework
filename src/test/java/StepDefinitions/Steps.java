package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static Helpers.Constants.*;

public class Steps {

    private Response response;
    private RequestSpecification request;

    @Given("the ExchangeRates API is available")
    public void the_exchange_rates_api_is_available() {
        RestAssured.baseURI = BASE_URL;
    }

    @When("a request is made to retrieve historical exchange rates for {string} between {string} and {string}")
    public void a_request_is_made_to_retrieve_historical_exchange_rates_for_between_and(String baseCurrency, String startDate, String endDate) {
        request = RestAssured.given()
                .queryParam("apikey", VALID_API_KEY)
                .queryParam("base", baseCurrency)
                .queryParam("start_date", startDate)
                .queryParam("end_date", endDate);

        response = request.get(ENDPOINT);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("the response should contain exchange rates for {string}")
    public void the_response_should_contain_exchange_rates_for(String targetCurrency) {
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains(targetCurrency));
    }

    @When("a request is made with incorrect authentication credentials")
    public void makeRequestWithoutAuthentication() {
        request = RestAssured.given()
                .queryParam("apikey", WRONG_API_KEY)
                .queryParam("base", "USD")
                .queryParam("start_date", "2022-01-01")
                .queryParam("end_date", "2022-01-07");

        response = request.get(ENDPOINT);
    }

    @Then("the response should contain {string} error message")
    public void verifyErrorMessage(String errorMessage) {
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains(errorMessage));
    }

    @When("a request is made using an unsupported HTTP method")
    public void makeRequestWithUnsupportedMethod() {
        request = RestAssured.given()
                .queryParam("apikey", VALID_API_KEY);
        //tried to use unsupported method to our endpoint to receive 405
        response = request.delete(ENDPOINT);
    }

    @When("a request is made to access a non-existing resource")
    public void makeRequestToNonExistingResource() {
        request = RestAssured.given()
                .queryParam("apikey", VALID_API_KEY);
        response = request.get("/non_existing_resource");
    }

    @When("a request is made to access a forbidden resource")
    public void makeRequestToForbiddenResource() {
        request = RestAssured.given()
                .queryParam("apikey", VALID_API_KEY);
        //sadly, I'm not aware which resource is forbidden for my api key
        response = request.get("/forbidden_resource");
    }

    @When("a request is made and the rate limit is exceeded")
    public void makeRequestWithExceededRateLimit() {
        request = RestAssured.given()
                .queryParam("apikey", VALID_API_KEY);
        // Simulate making multiple requests within a short time period to exceed the rate limit 250
        for (int i = 0; i < 10; i++) {
            response = request.get(ENDPOINT);
        }
    }

    @When("a request is made with invalid parameters")
    public void makeRequestWithInvalidParameters() {
        request = RestAssured.given()
                .queryParam("apikey", VALID_API_KEY)
                .queryParam("invalidParam", "value");

        response = request.get(ENDPOINT);
    }

}
