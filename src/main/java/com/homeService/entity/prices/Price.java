package com.homeService.entity.prices;

public class Price {
    private final Integer money;
    private final Integer penny;
    private final String currency;

    public Price(Integer money, Integer penny, String currency) throws Exception {
        this.currency = currency;
        if (penny > 99 || penny < 0) {
            throw new Exception("Penny diapason: [0 - 99], received value in penny: " + penny + ';');
        }
        if (money < 0) {
            throw new Exception("Money diapason: [0 - large], received value in money: " + money + ';');
        }

        this.money = money;
        this.penny = penny;
    }
    public Integer getMoney() {
        return money;
    }
    public Integer getPenny() {
        return penny;
    }
    public String getCurrency() {
        return currency;
    }
}
