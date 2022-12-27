package com.kucoin.helpers;

import com.kucoin.objects.symbol_ticker.TickerData;
import java.util.Comparator;

public class TickerComparatorLow implements Comparator<TickerData> {

    //the results return in String
    //we need to convert result field to Float
    @Override
    public int compare(TickerData o1, TickerData o2) {
        float result = Float.compare(Float.parseFloat(o1.getChangeRate()), Float.parseFloat(o2.getChangeRate()));
        return (int) result;
    }
}
