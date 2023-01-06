package com.kucoin.tests;

import com.kucoin.helpers.Specifications;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;


public class BaseTest {

    public static final String BASE_URL = "https://api.kucoin.com";
    public static final String CURRENCY_URL = "/api/v1/currencies";
    public static final String CURRENCY_CHAINS_URL = "/api/v2/currencies";
    public static final String ORDER_BOOK_URL = "api/v1/market/orderbook/level2_";
    public static final String SYMBOLS_AND_TICKERS_LINK = "/api/v1/market/allTickers";
    public static final String MARKET_STATISTICS_LINK = "/api/v1/market/stats";
    public static final String MARKET_LIST_LINK = "/api/v1/markets";
    public static final String TRADE_HISTORY_LINK = "/api/v1/market/histories";

    @BeforeEach
    public void beforeTest(){
        RestAssured.requestSpecification = new RequestSpecBuilder() .log(LogDetail.ALL).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL),
                Specifications.responseSpec(200));
    }
}

