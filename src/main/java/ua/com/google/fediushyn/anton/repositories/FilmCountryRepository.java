package ua.com.google.fediushyn.anton.repositories;

import ua.com.google.fediushyn.anton.model.FilmCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.util.List;

public interface FilmCountryRepository extends JpaRepository<FilmCountry, Long> {

    FilmCountry findByName(@Param("country") String country);

    FilmCountry findByCode(@Param("code") String code);

    boolean existsByName(@Param("country") String country);

    boolean existsByCode(@Param("code") String code);

    @Nonnull
    List<FilmCountry> findAllByOrderByName();

    boolean deleteFilmCountryById(@Param("id") Long id);

    void deleteFilmCountryByName(@Param("country") String country);
}
