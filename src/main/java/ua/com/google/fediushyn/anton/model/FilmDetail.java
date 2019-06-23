package ua.com.google.fediushyn.anton.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "film_details")
public class FilmDetail {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_film", referencedColumnName = "id")
    private Film film;

    private String director;
    private String cast;

    @Column(name = "images_url")
    private String imagesPath;

    @Column(name = "film_url")
    private String pathFilm;

    public FilmDetail() {}

    public FilmDetail(String cast, String director) {
        this.cast = cast;
        this.director = director;
    }

    public FilmDetail(String cast, String director, String pathFilm) {
        this.cast = cast;
        this.director = director;
        this.pathFilm = pathFilm;
    }

    public FilmDetail(String cast, String director, String pathFilm, String imagesPath) {
        this.cast = cast;
        this.director = director;
        this.pathFilm = pathFilm;
        this.imagesPath = imagesPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getPathFilm() {
        return pathFilm;
    }

    public void setPathFilm(String pathFilm) {
        this.pathFilm = pathFilm;
    }

    public List<String> getImagesPathes(){
      String imgsPath = imagesPath;
      if (imgsPath.length() > 0) {
          ArrayList<String> pathes = new ArrayList<>(Arrays.asList(imgsPath.split(",")));
          return pathes;
      }
      return new ArrayList<>();
    }

    public void setImagesPathes(List<String> imagesPathes){
        this.imagesPath = String.join(",", imagesPathes);
    }
}
