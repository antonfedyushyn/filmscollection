package ua.com.google.fediushyn.anton.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "film_genres")
public class FilmGenre {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "genre_name", nullable = false, unique = true)
    private String name;

    @Column(name = "genre_code", unique = true)
    private String code;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Film_FilmGenres",
            joinColumns = {@JoinColumn(name = "id_film_genre", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_film", referencedColumnName = "id")}
    )
    private List<Film> films = new ArrayList<Film>();

    public FilmGenre() {}

    public FilmGenre(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void addFilm(Film film){
        if (!films.contains(film)){
            films.add(film);
        }
        if (!film.getFilmGenres().contains(this)){
            film.getFilmGenres().add(this);
        }
    }

    public List<Film> getFilms(){
        return Collections.unmodifiableList(films);
    }

    public Page<Film> getFilms(Pageable pageable) {
        List<Film> films = getFilms();
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > films.size() ? films.size() : (start + pageable.getPageSize());
        return new PageImpl<Film>(films.subList(start, end), pageable, films.size());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
