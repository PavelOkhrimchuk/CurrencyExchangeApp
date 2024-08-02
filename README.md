# Currency Exchange App

Currency Exchange App is a RESTful service for managing currencies and exchange rates. The service allows for the creation, retrieval, and updating of currencies and exchange rates, as well as performing currency exchanges.

## Technologies Used

- **Java**: Core language for the application
- **Gradle**: Build tool
- **PostgreSQL**: Database
- **HikariCP**: Connection pool
- **Tomcat**: Servlet container
- **Docker**: Containerization
- **JDBC**: Java Database Connectivity

## Prerequisites

- **Java 17**
- **Docker**
- **Docker Compose**
- **Gradle**



## API Endpoints





```json
 GET /currencies
Get a list of all available currencies.


[
  { "id": 1, "fullName": "Australian Dollar", "code": "AUD", "sign": "A$" },
  { "id": 3, "fullName": "Euro", "code": "EUR", "sign": "€" },
  { "id": 4, "fullName": "Japanese Yen", "code": "JPY", "sign": "¥" },
  { "id": 5, "fullName": "British Pound", "code": "GBP", "sign": "£" },
  { "id": 6, "fullName": "Russian Ruble", "code": "RUB", "sign": "₽" }
]




 GET /currency/EUR
Get details for a specific currency.

{
  "id": 3,
  "fullName": "Euro",
  "code": "EUR",
  "sign": "€"
}



POST /currencies
Add a new currency.

{
  "id": 15,
  "fullName": "Romanian leu",
  "code": "RON",
  "sign": "L"
}


 GET /exchangeRates
Get exchange rates between currencies.

[
  {
    "id": 1,
    "baseCurrency": {
      "id": 1,
      "fullName": "Australian Dollar",
      "code": "AUD",
      "sign": "A$"
    },
    "targetCurrency": {
      "id": 2,
      "fullName": "United States Dollar",
      "code": "USD",
      "sign": "$"
    },
    "rate": 0.730000
  },
  {
    "id": 3,
    "baseCurrency": {
      "id": 3,
      "fullName": "Euro",
      "code": "EUR",
      "sign": "€"
    },
    "targetCurrency": {
      "id": 4,
      "fullName": "Japanese Yen",
      "code": "JPY",
      "sign": "¥"
    },
    "rate": 130.170000
  },
  {
    "id": 4,
    "baseCurrency": {
      "id": 4,
      "fullName": "Japanese Yen",
      "code": "JPY",
      "sign": "¥"
    },
    "targetCurrency": {
      "id": 5,
      "fullName": "British Pound",
      "code": "GBP",
      "sign": "£"
    },
    "rate": 0.006800
  }
]


 GET /exchangeRate/USDRUB
Get the exchange rate

{
  "id": 2,
  "baseCurrency": {
    "id": 2,
    "fullName": "United States Dollar",
    "code": "USD",
    "sign": "$"
  },
  "targetCurrency": {
    "id": 3,
    "fullName": "Euro",
    "code": "EUR",
    "sign": "€"
  },
  "rate": 0.86
}


 POST /exchangeRates
Add a new exchange rate.

{
  "id": 11,
  "baseCurrency": {
    "id": 2,
    "fullName": "United States Dollar",
    "code": "USD",
    "sign": "$"
  },
  "targetCurrency": {
    "id": 15,
    "fullName": "Romanian leu",
    "code": "RON",
    "sign": "L"
  },
  "rate": 4.61
}


 PATCH /exchangeRate/USDRUB
Update the exchange rate

{
  "id": 2,
  "baseCurrency": {
    "id": 2,
    "fullName": "Updated United States Dollar",
    "code": "USD",
    "sign": "$"
  },
  "targetCurrency": {
    "id": 3,
    "fullName": "Euro",
    "code": "EUR",
    "sign": "€"
  },
  "rate": 0.86
}


 GET /exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT
Convert an amount from one currency to another.

{
  "baseCurrency": {
    "id": 2,
    "fullName": "Updated United States Dollar",
    "code": "USD",
    "sign": "$"
  },
  "targetCurrency": {
    "id": 3,
    "fullName": "Euro",
    "code": "EUR",
    "sign": "€"
  },
  "amount": 100,
  "convertedAmount": 116.28
}

