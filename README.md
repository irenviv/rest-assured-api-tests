Rest-api testing framework, based on:
- Java 11
- Junit 5
- Rest Assured framework
- Gradle
- Allure Report

The framework consists of tests from two different websites:
- kucoin.com: 
  Link to documentation - https://docs.kucoin.com/#market-data
  . This is a cryptocurrency exchange that allows you to buy, sell, and trade Bitcoin and other cryptocurrencies. 
  Tests cover open API: to get Market Data (Symbols & Ticker, Order Book, Histories, Currencies)

- petstore.swagger: 
  Link to documentation - https://petstore.swagger.io/
  . This is a sample pet store server, where you can manipulate with user(create, delete, login, logout),
  pets(add pet to store, update, delete, find by id,etc), petstore orders (pet inventory, find purchase order, delete purchase order)
  
  Framework consists of such parts:
  - helpers (Specifications, comparators)
  - objects (pojo objects)
  - tests
  
  
