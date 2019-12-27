package ua.com.google.fediushyn.anton.repositories;


import ua.com.google.fediushyn.anton.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByLogin(@Param("login") String login);

    boolean existsByLogin(@Param("login") String login);

    boolean existsById(@Param("id") @Nullable Long id);

    @Nonnull
    List<CustomUser> findAll();
}
