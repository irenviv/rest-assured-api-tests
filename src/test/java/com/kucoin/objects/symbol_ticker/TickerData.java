package com.kucoin.objects.symbol_ticker;

public class TickerData {
    private String symbol;  // symbol
    private String symbolName; // Name of trading pairs, it would change after renaming
    private String buy; // bestAsk
    private String sell; // bestBid
    private String changeRate;  // 24h change rate
    private String changePrice;  // 24h change price
    private String high; // 24h highest price
    private String low;  // 24h lowest price
    private String vol;  // 24h volume，the aggregated trading volume in BTC
    private String volValue;  // 24h total, the trading volume in quote currency of last 24 hours
    private String last;  // last price
    private String averagePrice; // 24h average transaction price yesterday
    private String takerFeeRate; // Basic Taker Fee
    private String makerFeeRate;   // Basic Maker Fee
    private String takerCoefficient;  // Taker Fee Coefficient
    private String makerCoefficient;

    public TickerData(){}

    public TickerData(String symbol, String symbolName, String buy, String sell, String changeRate, String changePrice, String high, String low, String vol, String volValue, String last, String averagePrice, String takerFeeRate, String makerFeeRate, String takerCoefficient, String makerCoefficient) {
        this.symbol = symbol;
        this.symbolName = symbolName;
        this.buy = buy;
        this.sell = sell;
        this.changeRate = changeRate;
        this.changePrice = changePrice;
        this.high = high;
        this.low = low;
        this.vol = vol;
        this.volValue = volValue;
        this.last = last;
        this.averagePrice = averagePrice;
        this.takerFeeRate = takerFeeRate;
        this.makerFeeRate = makerFeeRate;
        this.takerCoefficient = takerCoefficient;
        this.makerCoefficient = makerCoefficient;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public String getBuy() {
        return buy;
    }

    public String getSell() {
        return sell;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public String getChangePrice() {
        return changePrice;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getVol() {
        return vol;
    }

    public String getVolValue() {
        return volValue;
    }

    public String getLast() {
        return last;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public String getTakerFeeRate() {
        return takerFeeRate;
    }

    public String getMakerFeeRate() {
        return makerFeeRate;
    }

    public String getTakerCoefficient() {
        return takerCoefficient;
    }

    public String getMakerCoefficient() {
        return makerCoefficient;
    }
}
