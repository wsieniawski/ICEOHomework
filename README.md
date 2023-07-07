# ICEOHomework
recrutation homework for ICEO company

Endpoint: https://api.apilayer.com/exchangerates_data/timeseries

Link for Documentation: https://apilayer.com/marketplace/exchangerates_data-api
> Documentation > GET /timeseries


Tests:
> Client error responses
  > 400 - invalid parameter start date
  > 401 - use wrong api key
  > 403 - try to reach out contant that you don't have rights to access - sadly, I'm not aware which one it might be
  > 404 - non existing resource
  > 405 - unsupported http method, tried DELETE but it didn't work
  > 429 - too many requests, based on documentation will happen once we reach 250 requests in total per month
> Required fields check
  > empty start date
  > empty end date
  > both empty
> Server error responses
  > 503 - DDOS? :)
> Restrive Exchange Rate Data
  > simple 200
