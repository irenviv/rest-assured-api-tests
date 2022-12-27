package com.kucoin.tests;

import com.kucoin.objects.symbol_ticker.TickerData;
import com.kucoin.objects.symbol_ticker.TickerDataShort;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.kucoin.helpers.TickerComparatorLow;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("cryptocurrency")
public class SymbolsAndTickersTests extends BaseTest {

    private final String exchangeToCurrency = "USDT";

    private List<TickerData> getTickers(){
        return given()
                .when()
                    .get("/api/v1/market/allTickers")
                .then()
                    .extract().jsonPath().getList("data.ticker", TickerData.class);
    }

    @Test
    public void filterCryptoCurrenciesTest(){
        //get all cryptocurrency that ends with usdt
        List<TickerData> usdTickers = getTickers().stream()
                .filter(x -> x.getSymbol().endsWith(exchangeToCurrency)).toList();
        //assert all cryptocurrency ends with usdt
        assertTrue(usdTickers.stream().allMatch(x -> x.getSymbol().endsWith(exchangeToCurrency)));
    }

    @Test
    public void sortCryptoCurrencyWithTheHighestPriceInTheLast24HoursTest(){
        //sort dollars by price
        //Comparator helps to compare exact fields in objects
        List<TickerData> highToLow = getTickers().stream()
                .filter(x -> x.getSymbol().endsWith(exchangeToCurrency)).sorted(new Comparator<TickerData>() {
                    @Override
                    public int compare(TickerData o1, TickerData o2) {
                        return o2.getChangeRate().compareTo(o1.getChangeRate());
                    }
                }).toList();

        List<TickerData> top10 = highToLow.stream().limit(10).toList();
        System.out.println("first symbol - " + top10.get(0).getSymbol());
        assertEquals("FLAME-USDT",top10.get(0).getSymbol());
    }

    @Test
    public void sortCryptoCurrencyWithTheLowestPriceInTheLast24HoursTest() {
        List<TickerData> lowToHigh = getTickers().stream()
                .filter(x -> x.getSymbol().endsWith(exchangeToCurrency))
                .sorted(new TickerComparatorLow())
                .limit(5)
                .toList();
        System.out.println("the lowest cryptocurrency: " + lowToHigh.get(0).getSymbol());
        assertEquals(lowToHigh.get(0).getSymbol(), "LTO-USDT");
    }

    @Test
    public void getTickerStatisticsInTheLast24HoursTest(){
        given()
                    .param("symbol", "BTC-USDT")
                .when()
                    .get("/api/v1/market/stats")
                .then()
                     .body("data.symbol", equalTo("BTC-USDT"))
                     .body("data.buy", notNullValue())
                     .body("data.sell", notNullValue())
                     .body("data.changeRate", notNullValue())
                     .body("data.changePrice", notNullValue())
                     .body("data.high", notNullValue())
                     .body("data.low", notNullValue())
                     .body("data.last", notNullValue())
                     .body("data.averagePrice", notNullValue());
    }

    @Test
    public void getMarketListCurrenciesTest(){
        Response response  = given()
                .when()
                    .get("/api/v1/markets")
                .then()
                    .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> actualCurrenciesList = jsonPath.getList("data");
        List<String> expectedCurrenciesList = new ArrayList<>(
                List.of("USDS",
                        "BTC",
                        "KCS",
                        "ALTS",
                        "NFT-ETF",
                        "FIAT",
                        "DeFi",
                        "NFT",
                        "Metaverse",
                        "Polkadot",
                        "ETF"));
        assertEquals(11, actualCurrenciesList.size());
        for(int i=0; i<actualCurrenciesList.size();i++){
            assertEquals(expectedCurrenciesList.get(i), actualCurrenciesList.get(i));
        }
    }

    //as example how to work with map
    public void map(){
        //first way to get currency and change rate via map
        List<String> currencySymbolsToLowerCase = getTickers().stream()
                .map(x->x.getSymbol().toLowerCase())
                .collect(Collectors.toList());
        Map<String, Float> usd = new HashMap<>();
        //fill usd card with forEach() method
        getTickers().forEach(x-> usd.put(x.getSymbol(), Float.parseFloat(x.getChangeRate())));

        //second way to get currency and change rate via list, by creating class TickerDataShort
        List<TickerDataShort> tickerDataShort = new ArrayList<>();
        getTickers().forEach(x-> tickerDataShort.add(
                new TickerDataShort(x.getSymbolName(), Float.parseFloat(x.getChangeRate()))));
    }


}
