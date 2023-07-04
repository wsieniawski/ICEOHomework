package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.Objects;

import static Helpers.Constants.VALID_API_KEY;
import static Helpers.Constants.WRONG_API_KEY;
import static Helpers.ResponseParser.getMessageFromJson;

public class Steps {

    private Response response;
    private RequestSpecification request;

    @Given("the ExchangeRates API is available")
    public void the_exchange_rates_api_is_available() {
        RestAssured.baseURI = "https://api.apilayer.com";
    }

    @When("a request is made to retrieve historical exchange rates for {string} between {string} and {string}")
    public void a_request_is_made_to_retrieve_historical_exchange_rates_for_between_and(String baseCurrency, String startDate, String endDate) {
        // Prepare the request
        request = RestAssured.given().log().all()
                .queryParam("apikey", VALID_API_KEY)
                .queryParam("base", baseCurrency)
                .queryParam("start_date", startDate)
                .queryParam("end_date", endDate);

        // Make the API call
        response = request.get("/exchangerates_data/timeseries");

    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        // Verify the response status code
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("the response should contain exchange rates for {string}")
    public void the_response_should_contain_exchange_rates_for(String targetCurrency) {
        // Verify that the response contains exchange rates for the target currency
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains(targetCurrency));
    }

    @When("a request is made without providing valid authentication credentials")
    public void makeRequestWithoutAuthentication() {
        request = RestAssured.given()
                .queryParam("apikey", WRONG_API_KEY)
                .queryParam("base", "USD")
                .queryParam("symbols", "EUR");

        response = request.get("/exchangerates_data/timeseries");
    }

    @Then("the response should contain an {string} error message")
    public void verifyUnauthorizedErrorMessage(String errorMessage) {
        String responseBody = response.getBody().asString();
        String messageFromJson = getMessageFromJson(responseBody);
        Assert.assertTrue(messageFromJson.contains(errorMessage));
    }

}
