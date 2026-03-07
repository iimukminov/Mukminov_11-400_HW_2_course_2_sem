package ru.kpfu.itis.mukminov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.mukminov.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);

    Optional<User> getUserByName(String name);

    @Query(value = "select * from users u where u.name = ?1", nativeQuery = true)
    Optional<User> getUserByNameNative(String name);


}
