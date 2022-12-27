package com.kucoin.objects.currencies;

public class CurrencyChainData {
    private String chainName;
    private String chain;
    private String withdrawalMinSize;
    private String withdrawalMinFee;
    private Boolean isWithdrawEnabled;
    private Boolean isDepositEnabled;
    private Integer confirms;
    private String contractAddress;

    public CurrencyChainData(String chainName, String chain, String withdrawalMinSize, String withdrawalMinFee, Boolean isWithdrawEnabled, Boolean isDepositEnabled, Integer confirms, String contractAddress) {
        this.chainName = chainName;
        this.chain = chain;
        this.withdrawalMinSize = withdrawalMinSize;
        this.withdrawalMinFee = withdrawalMinFee;
        this.isWithdrawEnabled = isWithdrawEnabled;
        this.isDepositEnabled = isDepositEnabled;
        this.confirms = confirms;
        this.contractAddress = contractAddress;
    }

    public String getChainName() {
        return chainName;
    }

    public String getChain() {
        return chain;
    }

    public String getWithdrawalMinSize() {
        return withdrawalMinSize;
    }

    public String getWithdrawalMinFee() {
        return withdrawalMinFee;
    }

    public Boolean getWithdrawEnabled() {
        return isWithdrawEnabled;
    }

    public Boolean getDepositEnabled() {
        return isDepositEnabled;
    }

    public Integer getConfirms() {
        return confirms;
    }

    public String getContractAddress() {
        return contractAddress;
    }
}


