package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmYear;

import java.util.List;

public interface FilmYearService {

    FilmYear getFilmYear(String year);

    boolean existsFilmYear(String year);

    List<FilmYear> getFilmYears();

    List<Film> geFilms(String year);

    void addFilmYear(String year) throws FilmException;

    void deleteFilmYear(String year);
}
