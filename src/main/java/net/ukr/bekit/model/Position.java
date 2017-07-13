package net.ukr.bekit.model;

import net.ukr.bekit.model.positionsEnums.PositionStatusType;
import net.ukr.bekit.model.positionsEnums.PositionType;

import javax.persistence.*;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by Александр on 29.05.2017.
 */
@Entity
@Table(name = "Positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String opened;
    private String closed;
    private double openPrice;
    private double currentPrice;
    private double close;
    private int amount;
    private double currentResult;
    private String stock;
    private String type;
    private String status;

    private String stringResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private CustomUser user;

    public Position() {
    }

    public double getCurrentPrice() throws IOException {
        SingleStock stock = new SingleStock();
        stock.getByTicker(this.stock);
        return currentPrice = stock.getPrice().doubleValue();
    }

    public double getCurrentResult() throws IOException {
        if (this.status.equalsIgnoreCase("OPENED")) {

            if (type.equalsIgnoreCase("LONG")) {
                double temp = (getCurrentPrice() - getOpenPrice()) * amount;
                return currentResult = temp;
            } else {
                double temp = (getOpenPrice() - getCurrentPrice()) * amount;
                return currentResult = temp;
            }
        }
        return currentResult;
    }

    public String getStringResult() throws IOException {

        return this.stringResult = new DecimalFormat("#0.00").format(this.getCurrentResult());
    }

    public String convertPositionTypeintoOrderType() {
        if (type.equalsIgnoreCase("LONG")) {
            return "buy";
        } else {
            return "sell";
        }
    }

    public void makeClone(Position position) {
        this.opened = position.getOpened();
        this.status = position.getStatus();
        this.stock = position.getStock();
        this.type = position.getType();
        this.openPrice = position.getOpenPrice();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setCurrentResult(double currentResult) {
        this.currentResult = currentResult;
    }

    public String getType() {
        return type;
    }

    public void setType(PositionType type) {
        this.type = type.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(PositionStatusType status) {
        this.status = status.toString();
    }
}
