package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.model.*;
import ua.com.google.fediushyn.anton.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    @Autowired
    FilmService(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    @Transactional(readOnly = true)
    public Film getFilm(String film) {
        return filmRepository.findByName(film);
    }

    @Transactional(readOnly = true)
    public Film getFilmByCode(String code) {
        return filmRepository.findByCode(code);
    }

    @Transactional(readOnly = true)
    public Film getFilmByID(Long idFilm) {
        return filmRepository.findFilmById(idFilm);
    }

    @Transactional(readOnly = true)
    public boolean existsFilm(String film) {
        return filmRepository.existsByName(film);
    }

    @Transactional(readOnly = true)
    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Film> getAllFilms(Pageable pageable) {
        return filmRepository.findAllFilms(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Film> getFilmsByName(String text, Pageable pageable){
        return filmRepository.findByName(text, pageable);
    }

    @Transactional(readOnly = true)
    public long getCountAllFilms(){
        return filmRepository.countFilms();
    }

    @Transactional
    public void addFilm(String filmName, FilmYear filmYear, FilmCountry filmCountry,
                           String notes, FilmQualities quality, String duration, FilmTranslation translation,
                           Date premiereDate, String posterUrl, String posterFileName, List<FilmGenre> filmGenres, FilmDetail filmDetail) throws FilmException{
        if (filmRepository.existsByName(filmName)) {
            throw new FilmException("Film.is.exist", "Film %s is exists!", filmName);
        }
        if (filmName.isEmpty()) {
            throw new FilmException("Film.name.is.empty", "Film name is empty!");
        }
        Film film = new Film(filmName, notes, quality, duration, translation, premiereDate, posterUrl);
        film.setPosterFileName(posterFileName);

        film.setDateUpload(new Date());
        film.setYear(filmYear);
        film.setCountry(filmCountry);
        for (FilmGenre filmGenre: filmGenres) {
            film.addGenre(filmGenre);
        }
        film.setFilmDetail(filmDetail);
        film.generateCode();

        filmRepository.save(film);
    }

    @Transactional
    public void saveFilm(Film film){
        filmRepository.save(film);
    }

    @Transactional
    public void deleteFilm(String filmName) {
        if (!filmRepository.existsByName(filmName)) return;
        filmRepository.deleteByName(filmName);
    }

    @Transactional
    public void deleteFilmByID(Long filmID) throws FilmException{
        if (!filmRepository.existsById(filmID)) {
            throw new FilmException("Films.not.found", "Movies not found!");
        }
        filmRepository.deleteById(filmID);
    }
}
