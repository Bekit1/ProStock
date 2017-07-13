package net.ukr.bekit.services.repositories;

import net.ukr.bekit.model.CustomUser;
import net.ukr.bekit.model.Position;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Александр on 08.06.2017.
 */
public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query("SELECT u FROM Position u where u.id = :id")
    Position findById(@Param("id") long id);

    @Query("SELECT u FROM Position u where  u.user.id=:user AND u.status='CLOSED'")
    List<Position> findByStatus(@Param("user") long id, Pageable pageable);

    @Query("SELECT u FROM Position u where  u.user.id=:user")
    List<Position> findByUserId(@Param("user") long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Position u WHERE u.user.id=:user AND u.status ='OPENED'")
    boolean existsByStatus(@Param("user") long id);

    @Query("SELECT COUNT(c) FROM Position c WHERE c.user.id = :userId")
    long countPositionByUserId(@Param("userId") long id);

    @Query("SELECT COUNT(c) FROM Position c WHERE c.user.id = :userId AND c.status='CLOSED'")
    long countClosedPositionByUserId(@Param("userId") long id);

    @Query("SELECT c FROM Position c WHERE c.user.id = :userId")
    List<Position> findByUserId(@Param("userId") long id, Pageable pageable);
}
