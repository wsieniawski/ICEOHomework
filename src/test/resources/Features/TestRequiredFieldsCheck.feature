@sanity @regression
Feature: Check if required fields are actually required

  Scenario Outline: Try to retrieve historical data without required fields
    Given the ExchangeRates API is available
    When a request is made to retrieve historical exchange rates for "<baseCurrency>" between "<startDate>" and "<endDate>"
    Then the response status code should be 400
    And the response should contain "You have specified an invalid" error message

    Examples:
      | baseCurrency | startDate  | endDate    |
      | USD          | null       | 2022-01-07 |
      | EUR          | 2022-03-15 | null       |
      | GBP          | null       | null       |
