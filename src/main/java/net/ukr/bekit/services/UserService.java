package net.ukr.bekit.services;

import net.ukr.bekit.model.CustomUser;
import net.ukr.bekit.model.Order;
import net.ukr.bekit.model.Position;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface UserService {
    CustomUser getUserByLogin(String login);

    CustomUser getUserById(long id);

    boolean existsByLogin(String login);

    void addUser(CustomUser customUser);

    void addOrder(CustomUser customUser, Order order) throws IOException;

    void executeOrder(Order order) throws IOException;

    Order getOrderById(long id);

    void cancelOrder(long id);

    void closePosition(Long id) throws IOException;

    boolean existByStatus(CustomUser customUser);

    double checkEnoughBalance(CustomUser customUser) throws IOException;

    void marginCall(CustomUser customUser) throws IOException;

    List<Position> findClosedPositions(long id, Pageable pageable);

    List<Order> findOrdersByUserId(long id, Pageable pageable);

    List<Position> findPositionsByUserId(long id, Pageable pageable);

    List<CustomUser> findAllUsers(Pageable pageable);

    long countOrderByUserId(long id);

    long countPositionByUserId(long id);

    long countClosedPositionByUserId(long id);

    long countUsers();


}
