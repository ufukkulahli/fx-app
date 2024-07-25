# FX API Use

---

## Health Check Endpoint

See if the App is running healthy

Request:
```
http://localhost:8080/api/v1/healthcheck
```

Response OK:
```
{
    "IsExternalFXAPIOK": true,
    "IsInMemoryDBOK": true
}
```

Response Fail:
```
{
    "IsExternalFXAPIOK": false,
    "IsInMemoryDBOK": false
}
```

---

## Exchange Rate Endpoint

Request:
```
http://localhost:8080/api/v1/fx/rate?from=EUR&to=USD
```

(If no 'from' and 'to' currencies are provided then default value 'USD' will be used for both parameters, like: from=USD&to=USD)

Response OK:
```
{
  "from": "EUR",
  "to": "USD",
  "rate": 1.0851045893,
  "success": true
}
```

Response Fail:
(In case of External FX API call fail)
```
{
  "from": "EUR",
  "to": "USD",
  "rate": 0,
  "success": false
}
```

---

## Currency Conversion Endpoint

Request:
```
http://localhost:8080/api/v1/fx/convert?from=EUR&to=TRY&amount=1
```

Response OK:
```
{
  "from": "EUR",
  "to": "TRY",
  "rate": 35.6019506538,
  "initialAmount": 1,
  "convertedAmount": 35.6019506538,
  "transactionID": "6c5125e2-2dd6-427a-8c64-db47486b7084",
  "date": "2024-07-24",
  "success": true
}
```

Response Fail:
```
{
  "from": "EUR",
  "to": "TRYx",
  "rate": 0,
  "initialAmount": 1,
  "convertedAmount": 0,
  "transactionID": "fa7f67ec-fa3a-4113-a40d-e03a7740592d",
  "date": "2024-07-24",
  "success": false
}
```

---

## Conversion History Endpoint

Request:
```
http://localhost:8080/api/v1/fx/history?transactionDate=2024-07-24&page=0&size=10
```

Parameters:
```
transactionDate=2024-07-24
transactionID=78b7657c-1f05-419c-b39c-ed45fdf06e29
page=0
size=10
```

- transactionDate or transactionID is mandatory!
- Use 'page' and 'size' parameters to paginate the results!

Response OK:
```
[
  {
    "from": "USD",
    "to": "USD",
    "rate": 1,
    "initialAmount": 1,
    "convertedAmount": 1,
    "transactionID": "78b7657c-1f05-419c-b39c-ed45fdf06e29",
    "date": "2024-07-24",
    "success": true
  },
  {
    "from": "USD",
    "to": "USD",
    "rate": 1,
    "initialAmount": 2,
    "convertedAmount": 2,
    "transactionID": "a69e9118-a1d1-4382-a29e-3d31dcdfff0c",
    "date": "2024-07-24",
    "success": true
  },
  {
    "from": "USD",
    "to": "USD",
    "rate": 1,
    "initialAmount": 3,
    "convertedAmount": 3,
    "transactionID": "6bcb71c0-3ad9-43ae-9cb4-0207d9124c39",
    "date": "2024-07-24",
    "success": true
  },
  {
    "from": "EUR",
    "to": "TRY",
    "rate": 35.6019506538,
    "initialAmount": 1,
    "convertedAmount": 35.6019506538,
    "transactionID": "6c5125e2-2dd6-427a-8c64-db47486b7084",
    "date": "2024-07-24",
    "success": true
  },
  {
    "from": "EUR",
    "to": "TRYx",
    "rate": 0,
    "initialAmount": 1,
    "convertedAmount": 0,
    "transactionID": "fa7f67ec-fa3a-4113-a40d-e03a7740592d",
    "date": "2024-07-24",
    "success": false
  },
  {
    "from": "EUR",
    "to": "TRY",
    "rate": 35.6019506538,
    "initialAmount": 1000,
    "convertedAmount": 35601.950653800006,
    "transactionID": "27332229-ef3d-499e-832f-fdcfb8805435",
    "date": "2024-07-24",
    "success": true
  }
]
```

Response Fail:
(either transactionID or transactionDate is missing)
```
{
  "message": "transactionID (like 6ca586ef-0a96-442c-9dec-35de89dfa8ba) or transactionDate (like 2024-07-25) is required",
  "status": 400
}
```

Status `400 Bad Request`

## Global Error Handling

General response:
```
{
    "error": "Internal Server Error",
    "message": "Some information"
}
```

Status `500 Internal Server Error`

Incorrect data given for a parameter; response:

```
{
    "error": "Internal Server Error",
    "message": "Failed to convert value of type 'java.lang.String' to required type 'java.util.UUID'; Invalid UUID string: 1",
    "cause": "transactionID"
}
```

Status `500 Internal Server Error`