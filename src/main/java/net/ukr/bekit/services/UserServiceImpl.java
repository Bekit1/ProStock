package net.ukr.bekit.services;

import net.ukr.bekit.model.*;
import net.ukr.bekit.model.ordersEnums.OrderType;
import net.ukr.bekit.model.ordersEnums.StatusType;
import net.ukr.bekit.model.positionsEnums.PositionStatusType;
import net.ukr.bekit.model.positionsEnums.PositionType;
import net.ukr.bekit.services.repositories.OrderRepository;
import net.ukr.bekit.services.repositories.PositionRepository;
import net.ukr.bekit.services.repositories.UserRepository;
import net.ukr.bekit.services.threads.MarginCallThread;
import net.ukr.bekit.services.threads.UpdaterThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @Transactional(readOnly = true)
    public CustomUser getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public CustomUser getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    @Transactional
    public void addUser(CustomUser customUser) {
        userRepository.save(customUser);
    }

    @Override
    @Transactional
    public void addOrder(CustomUser customUser, Order order) throws IOException {
        order.setCreated(newDate());
        order.setStatus(StatusType.WAITING);
        customUser.getOrders().add(order);
        order.setUser(customUser);
        orderRepository.save(order);
        UpdaterThread updaterThread = new UpdaterThread(order);
        applicationContext.getAutowireCapableBeanFactory().initializeBean(updaterThread, null);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(updaterThread);
        Thread updater = new Thread(updaterThread);
        updater.start();
        MarginCallThread marginCallThread = new MarginCallThread(customUser);
        applicationContext.getAutowireCapableBeanFactory().initializeBean(marginCallThread, null);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(marginCallThread);
        Thread margin = new Thread(marginCallThread);
        margin.start();
    }

    @Override
    @Transactional
    public void executeOrder(Order order) throws IOException {
        CustomUser customUser = order.getUser();
        SingleStock stock = new SingleStock();
        stock.getByTicker(order.getStock());
        int check = 0;
        int requireMorePosition = 0;
        Position morePosition = new Position();
        for (Position pos : customUser.getPositions()) {
            if (pos.getStatus().equalsIgnoreCase("opened") && pos.getStock().equalsIgnoreCase(stock.getTicker()) && !pos.convertPositionTypeintoOrderType().equalsIgnoreCase(order.getType())) {
                if (pos.getAmount() == order.getAmount()) {
                    pos.setClosed(newDate());
                    pos.setStatus(PositionStatusType.CLOSED);
                    order.setExecuted(newDate());
                    order.setStatus(StatusType.SUCCESS);
                    customUser.getAccount().setBalance(customUser.getAccount().getBalance() + pos.getCurrentResult());
                    orderRepository.save(order);
                    positionRepository.save(pos);
                    userRepository.save(customUser);
                } else {
                    if (pos.getAmount() > order.getAmount()) {
                        if (pos.getType().equalsIgnoreCase("long")) {
                            customUser.getAccount().setBalance(customUser.getAccount().getBalance() + (order.getAmount() * (pos.getCurrentPrice())));
                            Position partly = new Position();
                            partly.makeClone(pos);
                            pos.setAmount(pos.getAmount() - order.getAmount());
                            order.setExecuted(newDate());
                            order.setStatus(StatusType.SUCCESS);
                            partly.setAmount(order.getAmount());
                            partly.setClosed(newDate());
                            partly.getCurrentResult();
                            partly.setStatus(PositionStatusType.CLOSED);
                            morePosition = partly;
                            requireMorePosition = 1;
                            orderRepository.save(order);
                            positionRepository.save(pos);
                            userRepository.save(customUser);
                        } else {
                            customUser.getAccount().setBalance(customUser.getAccount().getBalance() + (order.getAmount() * (pos.getOpenPrice() - pos.getCurrentPrice())));
                            Position partly = new Position();
                            partly.makeClone(pos);
                            pos.setAmount(pos.getAmount() - order.getAmount());
                            order.setExecuted(newDate());
                            order.setStatus(StatusType.SUCCESS);
                            partly.setAmount(order.getAmount());
                            partly.setClosed(newDate());
                            partly.getCurrentResult();
                            partly.setStatus(PositionStatusType.CLOSED);
                            morePosition = partly;
                            requireMorePosition = 1;
                            orderRepository.save(order);
                            positionRepository.save(pos);
                            userRepository.save(customUser);
                        }
                    } else {
                        pos.setClosed(newDate());
                        pos.setStatus(PositionStatusType.CLOSED);
                        customUser.getAccount().setBalance(customUser.getAccount().getBalance() + pos.getCurrentResult());
                        Position position = new Position();
                        position.setStock(order.getStock());
                        position.setOpenPrice((stock.getPrice()).doubleValue());
                        order.setExecuted(newDate());
                        order.setStatus(StatusType.SUCCESS);
                        position.setStatus(PositionStatusType.OPENED);
                        position.setOpened(newDate());
                        position.setAmount(order.getAmount() - pos.getAmount());
                        position.setType(pos.getType().equalsIgnoreCase("long") ? "SHORT" : "LONG");
                        requireMorePosition = 1;
                        morePosition = position;
                        positionRepository.save(pos);
                        orderRepository.save(order);
                    }
                }
                check = 1;
            }
        }
        if (requireMorePosition == 1) {
            customUser.getPositions().add(morePosition);
            morePosition.setUser(customUser);
            positionRepository.save(morePosition);
            userRepository.save(customUser);
        }
        if (check == 0) {
            Position position = new Position();
            position.setStock(order.getStock());
            position.setOpenPrice((stock.getPrice()).doubleValue());
            order.setExecuted(newDate());
            if (order.getType().equalsIgnoreCase(OrderType.BUY.toString())) {
                position.setType(PositionType.LONG);
            } else {
                position.setType(PositionType.SHORT);
            }
            order.setStatus(StatusType.SUCCESS);
            position.setStatus(PositionStatusType.OPENED);
            position.setOpened(newDate());
            position.setAmount(order.getAmount());
            customUser.getPositions().add(position);
            position.setUser(customUser);
            orderRepository.save(order);
            positionRepository.save(position);
            userRepository.save(customUser);
        }
    }

    @Override
    @Transactional
    public void closePosition(Long id) throws IOException {
        Position position = positionRepository.findById(id);
        if (position.getStatus().equalsIgnoreCase("OPENED")) {
            SingleStock stock = new SingleStock();
            stock.getByTicker(position.getStock());
            position.setClose(stock.getPrice().doubleValue());
            position.setClosed(newDate());
            position.setStatus(PositionStatusType.CLOSED);
            if (position.getType().equalsIgnoreCase("LONG")) {
                position.setCurrentResult((stock.getPrice().doubleValue() - position.getOpenPrice()) * position.getAmount());
            } else {
                position.setCurrentResult((position.getOpenPrice() - stock.getPrice().doubleValue()) * position.getAmount());
            }
            position.getUser().getAccount().setBalance(position.getUser().getAccount().getBalance() + position.getCurrentResult());
        }
    }

    @Override
    @Transactional
    public void cancelOrder(long id) {
        Order order = orderRepository.findById(id);
        if (order.getStatus().equalsIgnoreCase("WAITING")) {
            order.setExecuted(newDate());
            order.setStatus(StatusType.FAIL);
        }
    }

    @Override
    @Transactional
    public List<Position> findClosedPositions(long id, Pageable pageable) {
        return positionRepository.findByStatus(id, pageable);
    }

    @Override
    @Transactional
    public Order getOrderById(long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public boolean existByStatus(CustomUser customUser) {
        return positionRepository.existsByStatus(customUser.getId());
    }

    @Override
    @Transactional
    public double checkEnoughBalance(CustomUser customUser) throws IOException {
        double currentBalance = customUser.getAccount().getBalance();
        for (Position position : customUser.getPositions()) {
            if (position.getStatus().equalsIgnoreCase("OPENED")) {
                currentBalance = currentBalance + position.getCurrentResult();
            }
        }
        return currentBalance;
    }

    @Override
    @Transactional
    public void marginCall(CustomUser customUser) throws IOException {
        for (Position position : customUser.getPositions()) {
            if (position.getStatus().equalsIgnoreCase("OPENED")) {
                closePosition(position.getId());
                System.out.println(position.getAmount());
            }
        }
        for (Order order : customUser.getOrders()) {
            if (order.getStatus().equalsIgnoreCase("WAITING")) {
                cancelOrder(order.getId());
                System.out.println(order.getAmount());
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrdersByUserId(long id, Pageable pageable) {
        return orderRepository.findByUserId(id, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Position> findPositionsByUserId(long id, Pageable pageable) {
        return positionRepository.findByUserId(id, pageable);
    }

    public String newDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(new Date());

    }

    @Override
    public List<CustomUser> findAllUsers(Pageable pageable) {
        return userRepository.findAllUsers(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long countOrderByUserId(long id) {
        return orderRepository.countOrderByUserId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countPositionByUserId(long id) {
        return positionRepository.countPositionByUserId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countClosedPositionByUserId(long id) {
        return positionRepository.countClosedPositionByUserId(id);
    }

    @Override
    public long countUsers() {
        return userRepository.countUsers();
    }
}