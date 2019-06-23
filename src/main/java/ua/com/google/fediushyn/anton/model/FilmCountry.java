package ua.com.google.fediushyn.anton.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "film_countries")
public class FilmCountry {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "country_name", unique = true)
    private String name;

    @Column(name = "country_code", unique = true)
    private String code;

    @OneToMany(mappedBy = "country")
    private List<Film> films = new ArrayList<Film>();

    public FilmCountry() {}

    public FilmCountry(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void addFilm(Film film){
        film.setCountry(this);
        films.add(film);
    }

    public List<Film> getFilms(){
        return Collections.unmodifiableList(films);
    }

    public Page<Film> getFilms(Pageable pageable){
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
