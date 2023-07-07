#Feature:
#
#  Scenario: Retrieve exchange rates from an unavailable API
#    Given the ExchangeRates API is not available
#    When a request is made to retrieve exchange rates
#    Then the response status code should be 503
#    And the response should contain a service unavailable error message