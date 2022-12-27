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

    @BeforeEach
    public void beforeTest(){
        RestAssured.requestSpecification = new RequestSpecBuilder() .log(LogDetail.ALL).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL),
                Specifications.responseSpec(200));
    }
}
