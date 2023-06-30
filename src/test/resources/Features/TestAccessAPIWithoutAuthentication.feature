Feature: Unauthorized user will get 401 error message

Scenario: Access the ExchangeRates API without authentication
Given the ExchangeRates API is available
When a request is made without providing valid authentication credentials
Then the response status code should be 401