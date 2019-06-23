package ua.com.google.fediushyn.anton.repositories;

import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmDetailRepository extends JpaRepository<FilmDetail, Long> {

    @Query("select case when count(fd) > 0 then true else false end from FilmDetail fd where fd.film = :film")
    boolean existsByFilm(@Param("film") Film film);

    @Query("select fd from FilmDetail fd where fd.film = :film")
    FilmDetail findByFilm(Film film);

    @Query("delete from FilmDetail fd where fd.id = :id")
    boolean deleteFilmDetailById(@Param("id") Long id);

    @Query("delete from FilmDetail fd where fd.film = film")
    boolean deleteByFilm(@Param("film") Film film);


}
