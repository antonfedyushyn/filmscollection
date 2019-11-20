package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface FilmService {
    Film getFilm(String film);

    Film getFilmByCode(String code);

    Film getFilmByID(Long idFilm);

    boolean existsFilm(String film);

    List<Film> getFilms();

    Page<Film> getAllFilms(Pageable pageable);

    Page<Film> getFilmsByName(String text, Pageable pageable);

    long getCountAllFilms();

    void addFilm(String filmName, FilmYear filmYear, FilmCountry filmCountry,
                 String notes, FilmQualities quality, String duration, FilmTranslation translation,
                 Date premiereDate, String posterUrl, String posterFileName, List<FilmGenre> filmGenres, FilmDetail filmDetail) throws FilmException;

    void saveFilm(Film film);

    void deleteFilm(String filmName);

    void deleteFilmByID(Long filmID) throws FilmException;
}
