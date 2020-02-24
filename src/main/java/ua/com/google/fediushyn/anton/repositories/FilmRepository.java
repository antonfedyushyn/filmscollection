package ua.com.google.fediushyn.anton.repositories;

import org.springframework.data.jpa.repository.Modifying;
import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmCountry;
import ua.com.google.fediushyn.anton.model.FilmGenre;
import ua.com.google.fediushyn.anton.model.FilmYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("select f from Film f where f.id = :id_film")
    Film findFilmById(@Param("id_film") Long id);

    Film findByName(@Param("name") String name);

    @Query("select f from Film f where f.code = :code order by f.dateUpload, f.id desc")
    Film findByCode(@Param("code") String code);

    boolean existsById(@Param("id_film") @Nullable Long id);

    boolean existsByName(@Param("film_name") String name);

    @Nonnull
    List<Film> findAllByOrderByDateUploadDesc();

    Page<Film> findAllByOrderByDateUploadDesc(Pageable pageable);

    @Query("SELECT count(f) FROM Film f ")
    long countFilms();

    @Query("SELECT f FROM Film f where f.name like concat('%', :name, '%') order by f.dateUpload desc, f.id desc")
    Page<Film> findByNameOrderByDateUploadDesc(@Param("name") String name, Pageable pageable);

    @Query("select f from Film f where f.country = :country order by f.dateUpload desc, f.id desc ")
    List<Film> findByFilmCountryOrderByDateUploadAndIdDesc(@Param("country") FilmCountry country);

    @Query("select f from Film f where f.filmGenres in :genre order by f.dateUpload desc, f.id desc ")
    List<Film> findByFilmGenresOrderByDateUploadAndIdDesc(@Param("genre") FilmGenre genre);

    @Query("select f from Film f where f.year in :year order by f.dateUpload desc, f.id desc ")
    List<Film> findByFilmYearOrderByDateUploadAndIdDesc(@Param("year") FilmYear year);

    @Modifying
    @Query("delete from Film f where f.id = :id_film")
    boolean deleteFilmById(@Param("id_film") Long id);

    @Modifying
    @Query("delete from Film f where f.name = :film_name")
    void deleteByName(@Param("film_name") String name);
}
