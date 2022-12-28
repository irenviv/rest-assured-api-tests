package com.kucoin.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeEach;
import swagger.petstore.helpers.Specifications;

public class BaseTest {

    public static final String BASE_URL = "https://api.kucoin.com";
    public static final String CURRENCY_URL = "/api/v1/currencies";
    public static final String CURRENCY_CHAINS_URL = "/api/v2/currencies";
    public static final String ORDER_BOOK_URL = "api/v1/market/orderbook/level2_";

    @BeforeEach
    public void beforeTest(){
        RestAssured.requestSpecification = new RequestSpecBuilder() .log(LogDetail.ALL).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL),
                Specifications.responseSpec(200));
    }
}
