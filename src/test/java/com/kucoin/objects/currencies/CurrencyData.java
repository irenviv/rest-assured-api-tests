package com.kucoin.objects.currencies;

public class CurrencyData {
    private String currency;
    private String name;
    private String fullName;
    private Integer precision;
    private Integer confirms;
    private String contractAddress;
    private Boolean isMarginEnabled;
    private Boolean isDebitEnabled;


    public CurrencyData(String currency, String name, String fullName, Integer precision, Integer confirms, String contractAddress, Boolean isMarginEnabled, Boolean isDebitEnabled) {
        this.currency = currency;
        this.name = name;
        this.fullName = fullName;
        this.precision = precision;
        this.confirms = confirms;
        this.contractAddress = contractAddress;
        this.isMarginEnabled = isMarginEnabled;
        this.isDebitEnabled = isDebitEnabled;
    }

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getPrecision() {
        return precision;
    }

    public Integer getConfirms() {
        return confirms;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public Boolean getIsMarginEnabled() {
        return isMarginEnabled;
    }

    public Boolean getIsDebitEnabled() {
        return isDebitEnabled;
    }
}



