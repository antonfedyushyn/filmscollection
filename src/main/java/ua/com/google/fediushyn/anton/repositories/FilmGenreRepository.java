package ua.com.google.fediushyn.anton.repositories;

import org.springframework.data.jpa.repository.Query;
import ua.com.google.fediushyn.anton.model.FilmGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface FilmGenreRepository extends JpaRepository<FilmGenre, Long> {
    FilmGenre findByName(@Param("genre") String genre);

    FilmGenre findByCode(@Param("code") String code);

    @Query("select g from FilmGenre g where g.id = :id")
    FilmGenre findFilmGenreById(@Param("id") Long id);

    boolean existsByName(@Param("genre") String genre);

    boolean existsByCode(@Param("code") String code);

    @Nonnull
    List<FilmGenre> findAllByOrderByNameAsc();

    boolean deleteFilmGenreById(@Param("id") Long id);

    void deleteFilmGenreByName(@Param("genre") String genre);
}
