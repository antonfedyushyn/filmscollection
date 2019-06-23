package ua.com.google.fediushyn.anton.repositories;

import ua.com.google.fediushyn.anton.model.FilmCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmCountryRepository extends JpaRepository<FilmCountry, Long> {
    @Query("SELECT fc FROM FilmCountry fc where fc.name = :country")
    FilmCountry findByName(@Param("country") String country);

    @Query("SELECT fc FROM FilmCountry fc where fc.code = :code")
    FilmCountry findByCode(@Param("code") String code);

    @Query("SELECT CASE WHEN COUNT(fc) > 0 THEN true ELSE false END FROM FilmCountry fc where fc.name = :country")
    boolean existsByName(@Param("country") String country);

    @Query("SELECT CASE WHEN COUNT(fc) > 0 THEN true ELSE false END FROM FilmCountry fc where fc.code = :code")
    boolean existsByCode(@Param("code") String code);

    @Query("SELECT fc FROM FilmCountry fc order by fc.name")
    List<FilmCountry> findAll();

    @Query("DELETE FROM FilmCountry fc WHERE fc.id = :id")
    boolean deleteFilmCountryById(@Param("id") Long id);

    @Query("DELETE FROM FilmCountry fc WHERE fc.name = :country")
    boolean deleteByName(@Param("country") String country);
}
