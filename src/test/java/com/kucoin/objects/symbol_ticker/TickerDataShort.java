package com.kucoin.objects.symbol_ticker;

public class TickerDataShort {

    private String name;
    private Float changeRate;

    public TickerDataShort(String name, Float changeRate) {
        this.name = name;
        this.changeRate = changeRate;
    }

    public String getName() {
        return name;
    }

    public Float getChangeRate() {
        return changeRate;
    }
}
