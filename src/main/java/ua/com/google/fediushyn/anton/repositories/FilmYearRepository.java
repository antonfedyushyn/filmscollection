package ua.com.google.fediushyn.anton.repositories;

import org.springframework.lang.NonNull;
import ua.com.google.fediushyn.anton.model.FilmYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmYearRepository extends JpaRepository<FilmYear, Long> {
    FilmYear findByName(@Param("name") String year);

    boolean existsByName(@Param("name") String year);

    @NonNull List<FilmYear> findAllByOrderByNameAsc();

    void deleteById(@Param("id") Long id);

    void deleteByName(@Param("year") String year);
}
