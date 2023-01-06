package com.kucoin.objects.symbol_ticker;

public class TickerDataShort {

    private String currencyPairName;
    private Float changeRate;

    public TickerDataShort(String name, Float changeRate) {
        this.currencyPairName = name;
        this.changeRate = changeRate;
    }

    public String getCurrencyPairName() {
        return currencyPairName;
    }

    public Float getChangeRate() {
        return changeRate;
    }

    @Override
    public String toString() {
        return  "{" + currencyPairName + ": " + changeRate +"}";
    }
}
