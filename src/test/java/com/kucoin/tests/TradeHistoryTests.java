package com.kucoin.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.IsNull.notNullValue;

@Tag("cryptocurrency")
public class TradeHistoryTests extends BaseTest {

    @Test
    public void verifyTradeHistoryDataTest(){
        given()
        .param("symbol", "BTC-USDT")
        .when()
        .get(TRADE_HISTORY_LINK)
        .then()
            .body("data.price", notNullValue())
            .body("data.size", notNullValue())
            .body("data.side", everyItem(either(containsString("buy")).or(containsString("sell"))))
            .body("data.time", notNullValue());
    }
}
