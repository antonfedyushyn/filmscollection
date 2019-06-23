package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmCountry;
import ua.com.google.fediushyn.anton.repositories.FilmCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FilmCountryService {
    @Autowired
    private FilmCountryRepository filmCountryRepository;

    @Transactional(readOnly = true)
    public FilmCountry getFilmCountry(String country) {
        return filmCountryRepository.findByName(country);
    }

    @Transactional(readOnly = true)
    public FilmCountry getFilmCountryByCode(String countryCode) {
        return filmCountryRepository.findByCode(countryCode);
    }

    @Transactional(readOnly = true)
    public boolean existsFilmCountry(String country) {
        return filmCountryRepository.existsByName(country);
    }

    @Transactional(readOnly = true)
    public List<FilmCountry> getFilmCounrties() {
        return filmCountryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Film> getFilms(String country) {
        if (!filmCountryRepository.existsByName(country)) return null;
        FilmCountry filmCountry = filmCountryRepository.findByName(country);
        return filmCountry.getFilms();
    }

    @Transactional(readOnly = true)
    public List<Film> getFilmsByCode(String code) {
        if (!filmCountryRepository.existsByCode(code)) return null;
        FilmCountry filmCountry = filmCountryRepository.findByCode(code);
        return filmCountry.getFilms();
    }

    @Transactional
    public void addFilmCountry(String country, String code) throws FilmException{
        if (filmCountryRepository.existsByName(country)) {
            throw new FilmException("Film.country.name.is.exist", "Film country name %s is exist!", country);
        }
        if (filmCountryRepository.existsByCode(code)){
            throw new FilmException("Film.country.code.is.exist", "Film country code %s is exist!", code);
        }
        if (country.isEmpty()) {
            throw new FilmException("Film.county.name.is.empty", "Film country name is empty!");
        }
        if (code.isEmpty()) {
            throw new FilmException("Film.county.code.is.empty", "Film country code is empty!");
        }
        FilmCountry filmCountry = new FilmCountry(country, code);
        filmCountryRepository.save(filmCountry);
    }

    @Transactional
    public void deleteFilmCountry(String country) {
        if (!filmCountryRepository.existsByName(country)) return;
        filmCountryRepository.deleteByName(country);
    }

}
