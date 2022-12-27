package com.kucoin.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("cryptocurrency")
public class OrderBookTests extends BaseTest {

    @Test
    void verifyAsksSortedFromLowToHighInPartOrderBookTest(){
        //get list of lists
        //"asks": [["6500.16", "0.57753524"],["6500.15", "0.57753524"]]
        var asks = given()
                    .param("symbol", "BTC-USDT")
                .when()
                    .get("/market/orderbook/level2_20")
                .then()
                    .extract().jsonPath().getList("data.asks", List.class);
        assertEquals(20, asks.size());

        //create list with asks price
        List<Float> actualAsksPrice  = new ArrayList<>();
        for(int i=0; i<asks.size(); i++){
            String price = (String) asks.get(i).get(0);
            actualAsksPrice.add(Float.parseFloat(price));
        }
        System.out.println("actualAsks: " + actualAsksPrice);

        //sort it
        List<Float> sortedAsksByPrice = actualAsksPrice.stream().sorted().collect(Collectors.toList());
        System.out.println("sortedAsksByPrice: " +  sortedAsksByPrice);
        //verify lists are sorted
        assertEquals(sortedAsksByPrice, actualAsksPrice);
    }

    @Test
    void verifyBidsSortedFromHighToLowInPartOrderBookTest(){
        var bids = given()
                .param("symbol", "BTC-USDT")
                .when()
                .get("/market/orderbook/level2_20")
                .then()
                .extract().jsonPath().getList("data.bids", List.class);
        assertEquals(20, bids.size());

        //create list with bids price
        List<Float> actualBidsPrice  = new ArrayList<>();
        for(int i=0; i<bids.size(); i++){
            String price = (String) bids.get(i).get(0);
            actualBidsPrice.add(Float.parseFloat(price));
        }
        System.out.println("actualBidsPrice: " + actualBidsPrice);

        //sort it
        List<Float> sortedBidsByPrice = actualBidsPrice.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        System.out.println("sortedAsksByPrice: " +  sortedBidsByPrice);
        //verify lists are sorted
        assertEquals(sortedBidsByPrice, actualBidsPrice);
    }
}
