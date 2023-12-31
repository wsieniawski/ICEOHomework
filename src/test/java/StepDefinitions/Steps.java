package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static Helpers.Constants.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Steps {

    private Response response;
    private RequestSpecification request;
    private String startDateRequired;
    private String emdDateRequired;

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
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    @Then("the response should contain exchange rates for {string}")
    public void the_response_should_contain_exchange_rates_for(String targetCurrency) {
        String responseBody = response.getBody().asString();
        assertThat(responseBody, containsString(targetCurrency));
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
        String message = errorMessage.toLowerCase();
        String responseBody = response.getBody().asString().toLowerCase();
        assertThat(responseBody, containsString(message));
    }

    @When("a request is made using an unsupported HTTP method")
    public void makeRequestWithUnsupportedMethod() {
        request = RestAssured.given()
                .queryParam("apikey", VALID_API_KEY)
                .queryParam("base", "USD")
                .queryParam("start_date", "2022-01-01")
                .queryParam("end_date", "2022-01-07");
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
        // for now it is '1' just not to reach 250 requests limit
        for (int i = 0; i < 1; i++) {
            response = request.get(ENDPOINT);
        }
    }

    @When("a request is made with invalid parameters")
    public void makeRequestWithInvalidParameters() {
        request = RestAssured.given()
                .queryParam("apikey", VALID_API_KEY)
                .queryParam("base", "USD")
                .queryParam("start_date", "2022-01>01")
                .queryParam("end_date", "2022-01-07");

        response = request.get(ENDPOINT);
    }

    @When("a request is made without {string}")
    public void a_request_is_made_without_required_parameter(String missingRequiredField) {
        switch (missingRequiredField) {
            case "startDate":
                startDateRequired = null;
                emdDateRequired = "2022-01-07";
                break;
            case "endDate":
                startDateRequired = "2022-01-01";
                emdDateRequired = null;
                break;
            case "both":
                startDateRequired = null;
                emdDateRequired = null;
                break;
            default:
                System.out.println("Missing required field is not specified.");
        }

        request = RestAssured.given()
                .queryParam("apikey", VALID_API_KEY)
                .queryParam("base", "USD")
                .queryParam("start_date", startDateRequired)
                .queryParam("end_date", emdDateRequired);
        response = request.get(ENDPOINT);
    }
}
