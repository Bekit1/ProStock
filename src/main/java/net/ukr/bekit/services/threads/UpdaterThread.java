package net.ukr.bekit.services.threads;

import net.ukr.bekit.model.Order;
import net.ukr.bekit.model.SingleStock;
import net.ukr.bekit.services.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.IOException;

/**
 * Created by Александр on 16.06.2017.
 */
//@Transactional(propagation= Propagation.REQUIRED)
public class UpdaterThread implements Runnable {
    private Order order;
    @Autowired
    private UserService userService;

    public UpdaterThread(Order order) {
        this.order = order;
    }

    public UpdaterThread() {
    }

    @Override
    public void run() {
        Thread th = Thread.currentThread();
        try {
            SingleStock stock = new SingleStock();
            stock.getByTicker(order.getStock());
            if (order.getType().equalsIgnoreCase("buy")) {
                for (; !th.isInterrupted() && stock.getPrice().doubleValue() > order.getPrice() && order.getStatus().equalsIgnoreCase("WAITING"); ) {
                    updateStock(stock);
                }
                if (order.getStatus().equalsIgnoreCase("WAITING"))
                    userService.executeOrder(order);
            }
            if (order.getType().equalsIgnoreCase("sell")) {
                for (; !th.isInterrupted() && stock.getPrice().doubleValue() <= order.getPrice() && order.getStatus().equalsIgnoreCase("WAITING"); ) {
                    updateStock(stock);
                }
                if (order.getStatus().equalsIgnoreCase("WAITING"))
                    userService.executeOrder(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void updateStock(SingleStock stock) throws IOException, InterruptedException {
        stock.getByTicker(order.getStock());
        stock.getPrice();
        System.out.println(stock.getPrice());
        Thread.currentThread().sleep(1000);
        order = userService.getOrderById(order.getId());
    }
}
