@sanity @regression
Feature: Check if required fields are actually required

  Scenario Outline: Try to retrieve historical data without required fields
    Given the ExchangeRates API is available
    When a request is made without "<missingRequiredField>"
    Then the response status code should be 400
    And the response should contain "You have not specified a Time-Frame" error message

    Examples:
      | missingRequiredField |
      | startDate            |
      | endDate              |
      | both                 |
