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

    private final String currencyChain = "BTC-USDT";
    private final int itemsInOrderBook = 20;

    private List<List> getAsksInOrderBook(int itemsInOrderBook){
        return given()
                .param("symbol", currencyChain)
                .when()
                .get(ORDER_BOOK_URL + itemsInOrderBook)
                .then()
                .extract().jsonPath().getList("data.asks", List.class);
    }

    private List<List> getBidsInOrderBook(int itemsInOrderBook){
        return given()
                .param("symbol", currencyChain)
                .when()
                .get(ORDER_BOOK_URL + itemsInOrderBook)
                .then()
                .extract().jsonPath().getList("data.bids", List.class);
    }

    @Test
    void verifyOrderBookConsists20AsksTest(){
        var asks = getAsksInOrderBook(itemsInOrderBook);
        assertEquals(itemsInOrderBook, asks.size());
    }

    @Test
    void verifyAsksSortedFromLowToHighInPartOrderBookTest(){
        //get list of lists
        //"asks": [["6500.16", "0.57753524"],["6500.15", "0.57753524"]]
        List<List> asks = getAsksInOrderBook(itemsInOrderBook);

        //create list with asks price
        System.out.println("actualAsks: ");
        List<Float> actualAsksPrice  = asks.stream()
                .map(x -> Float.parseFloat((String) x.get(0)))
                .peek(System.out::print)
                .collect(Collectors.toList());

        //sort it
        System.out.println("sortedAsksByPrice: ");
        List<Float> sortedAsksByPrice = actualAsksPrice.stream()
                .sorted()
                .peek(System.out::print)
                .collect(Collectors.toList());

        //verify lists are sorted
        assertEquals(sortedAsksByPrice, actualAsksPrice);
    }

    @Test
    void verifyOrderBookConsists20BidsTest(){
        var bids = getBidsInOrderBook(itemsInOrderBook);
        assertEquals(itemsInOrderBook, bids.size());
    }

    @Test
    void verifyBidsSortedFromHighToLowInPartOrderBookTest(){
        var bids = getBidsInOrderBook(itemsInOrderBook);

        //create list with bids price
        System.out.println("actualBidsPrice: ");
        List<Float> actualBidsPrice  = bids.stream()
                .map(x -> Float.parseFloat((String) x.get(0)))
                .peek(System.out::print)
                .collect(Collectors.toList());

        //sort it
        List<Float> sortedBidsByPrice = actualBidsPrice.stream()
                .sorted(Comparator.reverseOrder())
                .peek(System.out::print)
                .collect(Collectors.toList());

        //verify lists are sorted
        assertEquals(sortedBidsByPrice, actualBidsPrice);
    }
}
