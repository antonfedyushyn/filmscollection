package ua.com.google.fediushyn.anton.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "film_years")
public class FilmYear {
    @Id
    @GeneratedValue
    private long id;

    @Column(name="year_name", unique = true, length = 4)
    private String name;

    @OneToMany(mappedBy = "year")
    private final List<Film> films = new ArrayList<>();

    public FilmYear() {}

    public FilmYear(String name) {
        this.name = name;
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

    public List<Film> getFilms() {
        return Collections.unmodifiableList(films);
    }

    public Page<Film> getFilms(Pageable pageable) {
        List<Film> films = getFilms();
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > films.size() ? films.size() : (start + pageable.getPageSize());
        return new PageImpl<>(films.subList(start, end), pageable, films.size());
    }

    public void addFilm(Film film) {
        film.setYear(this);
        films.add(film);
    }
}
