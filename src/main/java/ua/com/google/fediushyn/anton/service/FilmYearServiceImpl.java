package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmYear;
import ua.com.google.fediushyn.anton.repositories.FilmYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FilmYearServiceImpl implements FilmYearService {
    private final FilmYearRepository filmYearRepository;

    @Autowired
    FilmYearServiceImpl(FilmYearRepository filmYearRepository){
        this.filmYearRepository = filmYearRepository;
    }

    @Transactional(readOnly = true)
    public FilmYear getFilmYear(String year) {
        return filmYearRepository.findByName(year);
    }

    @Transactional(readOnly = true)
    public boolean existsFilmYear(String year) {
        return filmYearRepository.existsByName(year);
    }

    @Transactional(readOnly = true)
    public List<FilmYear> getFilmYears() {
        return filmYearRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Film> geFilms(String year) {
        if (!filmYearRepository.existsByName(year)) return null;
        FilmYear filmYear = filmYearRepository.findByName(year);
        return filmYear.getFilms();
    }

    @Transactional
    public void addFilmYear(String year) throws FilmException {
        if (filmYearRepository.existsByName(year)) {
            throw new FilmException("Film.year.name.is.exist", "Film year name %s is exist!", year);
        }
        if (year.isEmpty()) {
            throw new FilmException("Film.year.name.is.empty", "Film year name is empty!");
        }

        try {
            Integer.parseInt(year);
        } catch (NumberFormatException e) {
            throw new FilmException("Film.year.name.is.incorrect", "Film year name %s is incorrect!", year);
        }
        FilmYear filmYear = new FilmYear(year);
        filmYearRepository.save(filmYear);
    }

    @Transactional
    public void deleteFilmYear(String year) {
        if (!filmYearRepository.existsByName(year)) return;
        filmYearRepository.deleteByName(year);
    }
}
