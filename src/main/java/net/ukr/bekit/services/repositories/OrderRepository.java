package net.ukr.bekit.services.repositories;


import net.ukr.bekit.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Александр on 08.06.2017.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT u FROM Order u where u.id = :id")
    Order findById(@Param("id") long id);

    @Query("SELECT c FROM Order c WHERE c.user.id = :userId")
    List<Order> findByUserId(@Param("userId") long id, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Order c WHERE c.user.id = :userId")
    long countOrderByUserId(@Param("userId") long id);
}
