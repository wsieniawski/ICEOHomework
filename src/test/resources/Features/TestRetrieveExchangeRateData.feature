@sanity @regression
Feature: Retrieve exchange rate data

  Scenario Outline: Retrieve historical exchange rates for specific currency and time frames
    Given the ExchangeRates API is available
    When a request is made to retrieve historical exchange rates for "<baseCurrency>" between "<startDate>" and "<endDate>"
    Then the response status code should be <expectedStatusCode>
    And the response should contain exchange rates for "<targetCurrency>"

    Examples:
      | baseCurrency | startDate   | endDate     | expectedStatusCode | targetCurrency |
      | USD          | 2022-01-01  | 2022-01-07  | 200                | EUR            |
      | EUR          | 2022-03-15  | 2022-03-20  | 200                | GBP            |
      | GBP          | 2022-06-01  | 2022-06-05  | 200                | USD            |