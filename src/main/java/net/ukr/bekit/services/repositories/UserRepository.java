package net.ukr.bekit.services.repositories;

import net.ukr.bekit.model.CustomUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    @Query("SELECT u FROM CustomUser u where u.login = :login")
    CustomUser findByLogin(@Param("login") String login);

    @Query("SELECT u FROM CustomUser u where u.id = :id")
    CustomUser findById(@Param("id") long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomUser u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);

    @Query("SELECT c FROM CustomUser c ")
    List<CustomUser> findAllUsers(Pageable pageable);

    @Query("SELECT COUNT(c) FROM CustomUser c ")
    long countUsers();
}
