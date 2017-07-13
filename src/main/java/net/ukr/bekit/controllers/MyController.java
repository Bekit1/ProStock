package net.ukr.bekit.controllers;

import net.ukr.bekit.model.*;
import net.ukr.bekit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by Александр on 03.06.2017.
 */
@Controller
public class MyController {
    static final int ITEMS_PER_PAGE = 10;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register";
        }
        if (login.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            String passHash = encoder.encodePassword(password, null);
            CustomUser dbUser = new CustomUser(login, passHash, UserRole.ADMIN);
            userService.addUser(dbUser);
        } else {
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            String passHash = encoder.encodePassword(password, null);
            CustomUser dbUser = new CustomUser(login, passHash, UserRole.USER);
            userService.addUser(dbUser);
        }
        return "redirect:/";
    }

    @RequestMapping("/")
    public String index(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserByLogin(login);
        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        if (dbUser.getRole().toString().equalsIgnoreCase("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else {
            return "index";
        }
    }

    @RequestMapping("/stocks")
    public String stocks() {
        return "stocks";
    }

    @RequestMapping("/orders")
    public String orders(@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserByLogin(login);
        List<Order> orders = userService.findOrdersByUserId(dbUser.getId(), new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));
        model.addAttribute("orders", orders);
        model.addAttribute("byOrdersPages", getPageCountForOrders(dbUser.getId()));
        return "orders";
    }

    @RequestMapping("/positions")
    public String positions(@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserByLogin(login);
        List<Position> positions = userService.findPositionsByUserId(dbUser.getId(), new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));
        model.addAttribute("positions", positions);
        model.addAttribute("byPositionsPages", getPageCountForPositions(dbUser.getId()));
        return "positions";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) throws IOException {
        SingleStock stock = new SingleStock();
        stock.getByTicker(pattern.toUpperCase());
        model.addAttribute("stock", stock);
        return "stocks";
    }

    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    public String placeOrder(@RequestParam String orderType, String ticker, String amount, String price, Model model) throws IOException {
        try {
            if (orderType != null && Integer.valueOf(amount) > 0 && Double.valueOf(price) > 0) {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String login = user.getUsername();
                CustomUser dbUser = userService.getUserByLogin(login);
                if (Integer.valueOf(amount) * Double.valueOf(price) > dbUser.getAccount().getBalance()) {
                    String error = "Not enough money";
                    model.addAttribute("error", error);
                    return "stocks";
                }
                if (!orderType.equalsIgnoreCase("buy") && !orderType.equalsIgnoreCase("sell")) {
                    String error = "Choose order type";
                    model.addAttribute("error", error);
                    return "stocks";
                }
                if (dbUser.getAccount().getBalance() <= 300) {
                    String error = "You have margin call on your account. Add funds";
                    model.addAttribute("error", error);
                    return "stocks";
                } else {
                    Order order = new Order(orderType, Double.parseDouble(price));
                    order.setStock(ticker);
                    order.setAmount(Integer.parseInt(amount));
                    order.setRequiredMoney();
                    userService.addOrder(dbUser, order);
                    model.addAttribute("order", order);
                    return "stocks";
                }
            }
        } catch (Exception e) {
            String error = "Wrong data";
            model.addAttribute("error", error);
            return "stocks";
        }
        return "stocks";
    }

    @RequestMapping(value = "/close/{id}")
    public String closePosition(@PathVariable(value = "id") long positionId, Model model) throws IOException {
        userService.closePosition(positionId);
        String closed = "Position has been closed";
        model.addAttribute("closed", closed);
        return "positions";
    }

    @RequestMapping(value = "/cancel/{id}")
    public String cancelOrder(@PathVariable(value = "id") long orderId, Model model) throws IOException {
        userService.cancelOrder(orderId);
        String canceled = "Order has been canceled";
        model.addAttribute("canceled", canceled);
        return "orders";
    }

    @RequestMapping(value = "/balance")
    public String balance(@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserByLogin(login);
        List<Position> positions = userService.findClosedPositions(dbUser.getId(), new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "closed"));
        model.addAttribute("positions", positions);
        model.addAttribute("balance", dbUser.getAccount());
        model.addAttribute("byClosedPositionsPages", getPageCountForClosedPositions(dbUser.getId()));
        return "balance";
    }


    private long getPageCountForOrders(long id) {
        long totalCount = userService.countOrderByUserId(id);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCountForPositions(long id) {
        long totalCount = userService.countPositionByUserId(id);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCountForClosedPositions(long id) {
        long totalCount = userService.countClosedPositionByUserId(id);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCountForUsers() {
        long totalCount = userService.countUsers();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    @RequestMapping("/admin")
    public String admin(@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
        List<CustomUser> customUsers = userService.findAllUsers(new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.ASC, "id"));
        model.addAttribute("users", customUsers);
        model.addAttribute("byUsersPages", getPageCountForUsers());
        return "admin";
    }
}
