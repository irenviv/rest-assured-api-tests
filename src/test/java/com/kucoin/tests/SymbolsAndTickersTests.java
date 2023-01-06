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
                .get(SYMBOLS_AND_TICKERS_LINK)
                .then()
                .extract().jsonPath().getList("data.ticker", TickerData.class);
    }

    @Test
    public void filterUSDTCryptoCurrenciesTest(){
        //get all cryptocurrency that ends with usdt
        List<TickerData> usdTickers = getTickers().stream()
                .filter(x -> x.getSymbol().endsWith(exchangeToCurrency)).toList();
        //assert all cryptocurrency ends with usdt
        assertTrue(usdTickers.stream().allMatch(x -> x.getSymbol().endsWith(exchangeToCurrency)));
    }

    @Test
    public void verifyCurrencyPairWithTheHighestRateInTheLast24HoursTest(){
        //filter currency that exchanged to dollar
        List<TickerData> filteredTickers = getTickers().stream()
                .filter(x -> x.getSymbol().endsWith(exchangeToCurrency)).limit(10).toList();

        //create list with tickers that consist currency pair name and change rate
        List<TickerDataShort> tickersShortData = new ArrayList<>();
        filteredTickers.forEach(x-> tickersShortData
                .add(new TickerDataShort(x.getSymbolName(), Float.parseFloat(x.getChangeRate()))));
        tickersShortData.forEach(System.out::print);

        //sort them
        List<TickerDataShort> sortedTickers = tickersShortData.stream().sorted(new Comparator<TickerDataShort>() {
                @Override
                public int compare(TickerDataShort o1, TickerDataShort o2) {
                    return o2.getChangeRate().compareTo(o1.getChangeRate());
                }
            }).toList();
        sortedTickers.forEach(System.out::print);

        //verify currency with the highest rate
        assertEquals("LYM-USDT", sortedTickers.get(0).getCurrencyPairName());
    }

    @Test
    public void verifyCurrencyPairWithTheLowestChangeRateInTheLast24HoursTest() {
        List<TickerData> lowToHigh = getTickers().stream()
                .filter(x -> x.getSymbol().endsWith(exchangeToCurrency))
                .sorted(new TickerComparatorLow())
                .limit(5)
                .toList();
        System.out.println("the lowest cryptocurrency: " + lowToHigh.get(0).getSymbol());
        assertEquals("SON-USDT", lowToHigh.get(0).getSymbol());
    }

    @Test
    public void getTickerStatisticsInTheLast24HoursTest(){
        String currencyPair = "BTC-USDT";
                given()
                    .param("symbol", currencyPair)
                .when()
                    .get(MARKET_STATISTICS_LINK)
                .then()
                     .body("data.symbol", equalTo(currencyPair))
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
    public void verifyMarketListOfCurrenciesTest(){
        Response response  = given()
                .when()
                .get(MARKET_LIST_LINK)
                .then()
                .extract().response();
        List<String> actualCurrenciesList = response.jsonPath().getList("data");
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
        assertEquals(expectedCurrenciesList, actualCurrenciesList);
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
