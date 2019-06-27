package ua.com.google.fediushyn.anton.repositories;

import org.springframework.lang.NonNull;
import ua.com.google.fediushyn.anton.model.FilmYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmYearRepository extends JpaRepository<FilmYear, Long> {
    @Query("select fy from FilmYear fy where fy.name = :year")
    FilmYear findByName(@Param("year") String year);

    @Query("select case when count(fy) > 0 then true else false end from FilmYear fy where fy.name = :year")
    boolean existsByName(@Param("year") String year);

    @Query("select fy from FilmYear fy order by fy.name")
    @NonNull List<FilmYear> findAll();

    @Query("delete from FilmYear fy where fy.id = :id")
    boolean deleteFilmYearById(@Param("id") Long id);

    @Query("delete from FilmYear fy where fy.name = :year")
    void deleteByName(@Param("year") String year);
}
