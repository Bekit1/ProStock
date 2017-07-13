package net.ukr.bekit.services.threads;

import net.ukr.bekit.model.CustomUser;
import net.ukr.bekit.model.Order;
import net.ukr.bekit.model.SingleStock;
import net.ukr.bekit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by HP Elitebook on 07.07.2017.
 */
public class MarginCallThread implements Runnable {
    private CustomUser customUser;
    @Autowired
    private UserService userService;

    public MarginCallThread(CustomUser customUser) {
        this.customUser = customUser;
    }

    public MarginCallThread() {
    }

    @Override
    public void run() {
        Thread th = Thread.currentThread();
        try {
            Thread.currentThread().sleep(30000);
            for (; !th.isInterrupted() && userService.existByStatus(userService.getUserById(customUser.getId())) && checkMarginCall(); ) {
                System.out.println("Enough money");
                Thread.currentThread().sleep(30000);
            }
            if (!checkMarginCall()) {
                userService.marginCall(userService.getUserById(customUser.getId()));
                System.out.println("Not enough money. Everything closed ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkMarginCall() throws IOException {
        double check = userService.checkEnoughBalance(userService.getUserById(customUser.getId()));
        return check > 2999.5;
    }
}
