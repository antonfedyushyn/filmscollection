package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmGenre;

import java.util.List;

public interface FilmGenreService {
    FilmGenre getFilmGenre(String genre);

    FilmGenre getFilmGenreByCode(String genreCode);

    FilmGenre getFilmGenreById(Long genreId);

    boolean existsFilmGenre(String genre);

    boolean existsFilmGenreByCode(String genreCode);

    List<FilmGenre> getFilmGenres();

    List<Film> getFilms(String genre);

    List<Film> getFilmsByCode(String code);

    void addFilmGenre(String genre, String code) throws FilmException;

    void deleteFilmGenre(String genre);
}
