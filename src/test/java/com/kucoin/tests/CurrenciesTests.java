package com.kucoin.tests;

import com.kucoin.objects.currencies.CurrencyChainData;
import com.kucoin.objects.currencies.CurrencyData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@Tag("cryptocurrency")
public class CurrenciesTests extends BaseTest {

    private String currency = "BTC";

    @Test
    public void getCurrenciesTest(){
        given()
        .when()
            .get(CURRENCY_URL)
        .then()
            .body("data.currency", notNullValue())
            .body("data.name", notNullValue())
            .body("data.fullName", notNullValue())
            .body("data.precision", notNullValue())
            .body("data.confirms", notNullValue())
            .body("data.withdrawalMinSize", notNullValue())
            .body("data.withdrawalMinFee", notNullValue())
            .body("data.isWithdrawEnabled", everyItem(either(is(true)).or(is(false))))
            .body("data.isDepositEnabled", everyItem(either(is(true)).or(is(false))))
            .body("data.isMarginEnabled", everyItem(either(is(true)).or(is(false))))
            .body("data.isDebitEnabled", everyItem(either(is(true)).or(is(false))));
    }

    @Test
    public void verifyCurrencyDetailsTest(){
        CurrencyData currencyData = given()
                .when()
                .get(CURRENCY_URL + "/" + currency)
                .then()
                .extract().jsonPath().getObject("data", CurrencyData.class);
        assertEquals(currency, currencyData.getCurrency());
        assertEquals(currency, currencyData.getName());
        assertEquals("Bitcoin", currencyData.getFullName());
        assertEquals(8, currencyData.getPrecision());
        assertTrue(currencyData.getIsMarginEnabled());
        assertTrue(currencyData.getIsDebitEnabled());
    }

    //TODO
    @Test
    public void verifyChainsInCurrencyTest(){
        List<CurrencyChainData> currencyChainData =
            given()
            .when()
                .get(CURRENCY_URL + "/" + currency)
            .then()
                .extract().jsonPath().getList("data.chains", CurrencyChainData.class);
        assertEquals(6, currencyChainData.size());

        List<String> expectedChains = new ArrayList<>(
                List.of("BTC",
                        "OPTIMISM",
                        "KCC",
                        "TRC20",
                        "ARBITRUM",
                        "BTC-Segwit"));
        for (int i =0; i<currencyChainData.size(); i++){
            assertEquals(expectedChains.get(i), currencyChainData.get(i).getChainName());
        }
    }
}
