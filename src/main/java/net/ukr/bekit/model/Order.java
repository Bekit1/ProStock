package net.ukr.bekit.model;

import net.ukr.bekit.model.ordersEnums.OrderType;
import net.ukr.bekit.model.ordersEnums.StatusType;

import javax.persistence.*;

/**
 * Created by Александр on 29.05.2017.
 */
@Entity
@Table(name="Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String created;
    private String executed;
    private String type;
    private String status;
    private String stock;
    private double price;
    private int amount;
    private double requiredMoney;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id")
    private CustomUser user;

    public Order() {
    }

    public Order(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
    }

    public String getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status.toString();
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getRequiredMoney() {
        return requiredMoney;
    }

    public void setRequiredMoney() {
        this.requiredMoney = amount*price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
