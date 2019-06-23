package ua.com.google.fediushyn.anton.repositories;

import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmCountry;
import ua.com.google.fediushyn.anton.model.FilmGenre;
import ua.com.google.fediushyn.anton.model.FilmYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("select f from Film f where f.id = :id_film order by f.dateUpload desc")
    Film findFilmById(@Param("id_film") Long id);

    @Query("select f from Film f where f.name = :film_name")
    Film findByName(@Param("film_name") String name);

    @Query("select f from Film f where f.code = :code")
    Film findByCode(@Param("code") String code);

    @Query("select case when count(f) > 0 then true else false end from Film f where f.id = :id_film")
    boolean existsById(@Param("id_film") Long id);

    @Query("select case when count(f) > 0 then true else false end from Film f where f.name = :film_name")
    boolean existsByName(@Param("film_name") String name);

    @Query("SELECT f FROM Film f order by f.dateUpload desc ")
    List<Film> findAll();

    @Query("SELECT f FROM Film f order by f.dateUpload desc ")
    Page<Film> findAllFilms(Pageable pageable);

    @Query("SELECT count(f) FROM Film f order by f.dateUpload ")
    long countFilms();

    @Query("SELECT f FROM Film f where f.name like concat('%', :film_text, '%')")
    Page<Film> findByName(@Param("film_text") String filmText, Pageable pageable);

    @Query("select f from Film f where f.country = :country order by f.dateUpload desc ")
    List<Film> findByCountry(@Param("country") FilmCountry country);

    @Query("select f from Film f where f.filmGenres in :genre order by f.dateUpload desc ")
    List<Film> findByFilmGenres(@Param("genre") FilmGenre genre);

    @Query("select f from Film f where f.year in :year order by f.dateUpload desc ")
    List<Film> findByFilmYear(@Param("year") FilmYear year);

    @Query("delete from Film f where f.id = :id_film")
    boolean deleteFilmById(@Param("id_film") Long id);

    @Query("delete from Film f where f.name = :film_name")
    boolean deleteByName(@Param("film_name") String name);
}
