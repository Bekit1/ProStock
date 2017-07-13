package net.ukr.bekit.model;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class SingleStock {

    private Stock stock;
    private String ticker;
    private BigDecimal price;
    private BigDecimal change;
    private BigDecimal peg;
    private BigDecimal dividend;

    public SingleStock() {
    }

    public void getByTicker(String ticker) throws IOException {
        this.ticker=ticker;
        stock = YahooFinance.get(ticker);
        ticker=stock.getName();
        price = stock.getQuote().getPrice();
        change = stock.getQuote().getChangeInPercent();
        peg = stock.getStats().getPeg();
        dividend = stock.getDividend().getAnnualYieldPercent();
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public BigDecimal getPrice() {
        return stock.getQuote().getPrice();
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getPeg() {
        return peg;
    }

    public void setPeg(BigDecimal peg) {
        this.peg = peg;
    }

    public BigDecimal getDividend() {
        return dividend;
    }

    public void setDividend(BigDecimal dividend) {
        this.dividend = dividend;
    }
}
