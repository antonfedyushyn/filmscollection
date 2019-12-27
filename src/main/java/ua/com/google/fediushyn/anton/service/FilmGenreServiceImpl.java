package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.model.Film;
import ua.com.google.fediushyn.anton.model.FilmGenre;
import ua.com.google.fediushyn.anton.repositories.FilmGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FilmGenreServiceImpl implements FilmGenreService{
    private final FilmGenreRepository filmGenreRepository;

    @Autowired
    public FilmGenreServiceImpl(FilmGenreRepository filmGenreRepository) {
        this.filmGenreRepository = filmGenreRepository;
    }

    @Transactional(readOnly = true)
    public FilmGenre getFilmGenre(String genre) {
        return filmGenreRepository.findByName(genre);
    }

    @Transactional(readOnly = true)
    public FilmGenre getFilmGenreByCode(String genreCode) {
        return filmGenreRepository.findByCode(genreCode);
    }

    @Transactional(readOnly = true)
    public FilmGenre getFilmGenreById(Long genreId) {
        return filmGenreRepository.findFilmGenreById(genreId);
    }

    @Transactional(readOnly = true)
    public boolean existsFilmGenre(String genre) {
        return filmGenreRepository.existsByName(genre);
    }

    @Transactional(readOnly = true)
    public boolean existsFilmGenreByCode(String genreCode) {
        return filmGenreRepository.existsByCode(genreCode);
    }

    @Transactional(readOnly = true)
    public List<FilmGenre> getFilmGenres() {
        return filmGenreRepository.findAllByOrderByNameAsc();
    }

    @Transactional(readOnly = true)
    public List<Film> getFilms(String genre) {
        if (!filmGenreRepository.existsByName(genre)) return null;
        FilmGenre filmGenre = filmGenreRepository.findByName(genre);
        return filmGenre.getFilms();
    }

    @Transactional(readOnly = true)
    public List<Film> getFilmsByCode(String code) {
        if (!filmGenreRepository.existsByCode(code)) return null;
        FilmGenre filmGenre = filmGenreRepository.findByCode(code);
        return filmGenre.getFilms();
    }

    @Transactional
    public void addFilmGenre(String genre, String code) throws FilmException{
        if (filmGenreRepository.existsByName(genre)) {
            throw new FilmException("Film.genre.name.is.exist", "Film genre name %s is exist!", genre);
        }
        if (filmGenreRepository.existsByCode(code)) {
            throw new FilmException("Film.genre.genre.is.exist", "Film genre genre %s is exist!", genre);
        }
        if (genre.isEmpty()) {
            throw new FilmException("Film.genre.name.is.empty", "Film genre name is empty!");
        }
        if (code.isEmpty()) {
            throw new FilmException("Film.genre.code.is.empty", "Film genre code is empty!");
        }
        FilmGenre filmGenre = new FilmGenre(genre, code);
        filmGenreRepository.save(filmGenre);
    }

    @Transactional
    public void deleteFilmGenre(String genre) {
        if (!filmGenreRepository.existsByName(genre)) return;
        filmGenreRepository.deleteFilmGenreByName(genre);
    }
}
