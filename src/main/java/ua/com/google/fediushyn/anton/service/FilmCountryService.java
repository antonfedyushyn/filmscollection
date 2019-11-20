package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmCountry;
import ua.com.google.fediushyn.anton.repositories.FilmCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FilmCountryService {

    FilmCountry getFilmCountry(String country);

    FilmCountry getFilmCountryByCode(String countryCode);

    boolean existsFilmCountry(String country);

    List<FilmCountry> getFilmCounrties();

    List<Film> getFilms(String country);

    List<Film> getFilmsByCode(String code);

    void addFilmCountry(String country, String code) throws FilmException;

    void deleteFilmCountry(String country);
}
