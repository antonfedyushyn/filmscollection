package ua.com.google.fediushyn.anton.repositories;

import org.springframework.lang.NonNull;
import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FilmDetailRepository extends JpaRepository<FilmDetail, Long> {

    boolean existsByFilm(@Param("film") Film film);

    FilmDetail findByFilm(Film film);

    void deleteById(@Param("id") Long id);

    void deleteByFilm(@Param("film") Film film);


}
