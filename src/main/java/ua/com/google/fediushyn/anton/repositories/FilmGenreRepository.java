package ua.com.google.fediushyn.anton.repositories;

import ua.com.google.fediushyn.anton.model.FilmGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmGenreRepository extends JpaRepository<FilmGenre, Long> {
    @Query("select fg from FilmGenre fg where fg.name = :genre")
    FilmGenre findByName(@Param("genre") String genre);

    @Query("select fg from FilmGenre fg where fg.code = :code")
    FilmGenre findByCode(@Param("code") String code);

    @Query("select fg from FilmGenre fg where fg.id = :id")
    FilmGenre findByID(@Param("id") int id);

    @Query("select case when count(fg) > 0 then true else false end from FilmGenre fg where fg.name = :genre")
    boolean existsByName(@Param("genre") String genre);

    @Query("select case when count(fg) > 0 then true else false end from FilmGenre fg where fg.code = :code")
    boolean existsByCode(@Param("code") String code);

    @Query("select fg from FilmGenre fg order by fg.name")
    List<FilmGenre> findAll();

    @Query("delete from FilmGenre fg where fg.id = :id")
    boolean deleteFilmGenreById(@Param("id") Long id);

    @Query("delete from FilmGenre fg where fg.name = :genre")
    void deleteByName(@Param("genre") String genre);
}
