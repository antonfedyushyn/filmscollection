package ua.com.google.fediushyn.anton.repositories;

import ua.com.google.fediushyn.anton.model.CustomUser;
import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmCommentRepository extends JpaRepository<FilmComment, Long> {

    @Query("select case when count(fc) > 0 then true else false end from FilmComment fc where fc.film = :film")
    boolean existsByFilm(@Param("film") Film film);

    @Query("select count(fc) from FilmComment fc where fc.film = :film")
    Long countFilmCommentByFilm(@Param("film") Film film);

    @Query("select count(fc) from FilmComment fc where fc.user = :user")
    Long countFilmCommentByUser(@Param("user") CustomUser user);

    @Query("select fc from FilmComment fc where fc.id = :id_comment")
    FilmComment findFilmCommentById(@Param("id_comment") Long id);

    @Query("select fc from FilmComment fc where fc.film = :film")
    List<FilmComment> findByFilm(@Param("film") Film film);

    @Query("delete from FilmComment fc where fc.id = :id_comment")
    boolean deleteFilmCommentById(@Param("id_comment") Long id);
}
