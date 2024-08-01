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

## Getting Started

### Clone the repository

git clone https://github.com/yourusername/currency-exchange-app.git
cd currency-exchange-app


### Build the Docker containers

docker-compose build


### Running the Application

После сборки контейнеров запустите приложение:

docker-compose up


Приложение будет доступно по адресу: [http://localhost:8081/CurrencyExchangeApp-1.0-SNAPSHOT/](http://localhost:8081/CurrencyExchangeApp-1.0-SNAPSHOT/.

## API Endpoints

Вы можете протестировать API-эндпоинты, используя такие инструменты, как Postman, или отправляя HTTP-запросы напрямую. Ниже приведены доступные эндпоинты:



```json
 GET /currencies
Получить список всех доступных валют.


[
  { "id": 1, "fullName": "Australian Dollar", "code": "AUD", "sign": "A$" },
  { "id": 3, "fullName": "Euro", "code": "EUR", "sign": "€" },
  { "id": 4, "fullName": "Japanese Yen", "code": "JPY", "sign": "¥" },
  { "id": 5, "fullName": "British Pound", "code": "GBP", "sign": "£" },
  { "id": 6, "fullName": "Russian Ruble", "code": "RUB", "sign": "₽" }
]




 GET /currency/EUR
Получить детали для конкретной валюты.

{
  "id": 3,
  "fullName": "Euro",
  "code": "EUR",
  "sign": "€"
}



POST /currencies
Добавить новую валюту.

{
  "id": 15,
  "fullName": "Romanian leu",
  "code": "RON",
  "sign": "L"
}


 GET /exchangeRates
Получить курсы обмена между валютами.

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
Получить курс обмена для USD на RUB.

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
Добавить новый курс обмена.

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
Обновить курс обмена для USD на RUB.

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
Конвертировать сумму из одной валюты в другую.

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

