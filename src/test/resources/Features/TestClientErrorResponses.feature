Feature: Client Error Responses are handled with correct codes

  Scenario: Retrieve exchange rates with an invalid request
    Given the ExchangeRates API is available
    When a request is made with invalid parameters
    Then the response status code should be 400
    And the response should contain an error message

  Scenario: Access the ExchangeRates API without authentication gives 401
    Given the ExchangeRates API is available
    When a request is made without providing valid authentication credentials
    Then the response status code should be 401
    And the response should contain an "Invalid authentication credentials" error message

  Scenario: Access a forbidden resource
    Given the ExchangeRates API is available
    When a request is made to access a forbidden resource
    Then the response status code should be 403
    And the response should contain a forbidden error message

  Scenario: Access a non-existing resource
    Given the ExchangeRates API is available
    When a request is made to access a non-existing resource
    Then the response status code should be 404
    And the response should contain a not found error message

  Scenario: Perform an unsupported HTTP method
    Given the ExchangeRates API is available
    When a request is made using an unsupported HTTP method
    Then the response status code should be 405
    And the response should contain a method not allowed error message

  Scenario: Handle HTTP response code 429 (Too Many Requests)
    Given the ExchangeRates API is available
    When a request is made and the rate limit is exceeded
    Then the response status code should be 429
    And the response should contain a message indicating the rate limit exceeded




