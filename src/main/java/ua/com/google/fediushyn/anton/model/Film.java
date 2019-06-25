package ua.com.google.fediushyn.anton.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToMany
    @JoinTable(
            name = "Film_FilmGenres",
            joinColumns = {@JoinColumn(name = "id_film", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_film_genre", referencedColumnName = "id")}
    )
    private final List<FilmGenre> filmGenres = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_country")
    private FilmCountry country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_year")
    private FilmYear year;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "dt_upload", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateUpload;

    @Column(name = "code", length = 36, nullable = false, unique = true)
    private String code;

    @Column(name = "notes", length = 2048)
    private String notes;

    @Column(name = "quality")
    private FilmQualities quality;

    @Column(name = "duration", length = 20)
    private String duration;

    @Column(name = "translation")
    private FilmTranslation translation;

    @Column(name = "dt_premiere", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date premiereDate;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "poster_file_name")
    private String posterFileName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment", fetch = FetchType.EAGER)
    private final List<FilmComment> filmComments = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "film")
    private FilmDetail filmDetail;

    public Film() {}

    public Film(String name){
        this.name = name;
    }

    public Film(String name, String notes, FilmQualities quality, String duration, FilmTranslation translation, Date premiereDate,
                String posterUrl) {
        this.name = name;
        this.notes = notes;
        this.quality = quality;
        this.duration = duration;
        this.translation = translation;
        this.premiereDate = premiereDate;
        this.posterUrl = posterUrl;
    }

    public void addGenre(FilmGenre filmGenre){
        if (!filmGenres.contains(filmGenre)){
            filmGenres.add(filmGenre);
        }
    }

    public List<FilmGenre> getFilmGenres() {
        return Collections.unmodifiableList(filmGenres);
    }

    public void addComment(FilmComment filmComment) {
        filmComment.setFilm(this);
        filmComments.add(filmComment);
    }

    public List<FilmComment> getComments() {
        return Collections.unmodifiableList(filmComments);
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

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    public FilmDetail getFilmDetail() {
        return filmDetail;
    }

    public void setFilmDetail(FilmDetail filmDetail) {
        filmDetail.setFilm(this);
        this.filmDetail = filmDetail;
    }

    public FilmCountry getCountry() {
        return country;
    }

    public void setCountry(FilmCountry country) {
        this.country = country;
    }

    public FilmYear getYear() {
        return year;
    }

    public void generateCode(){
        this.code = UUID.randomUUID().toString();
    }

    public void setYear(FilmYear year) {
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public FilmQualities getQuality() {
        return quality;
    }

    public void setQuality(FilmQualities quality) {
        this.quality = quality;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public FilmTranslation getTranslation() {
        return translation;
    }

    public void setTranslation(FilmTranslation translation) {
        this.translation = translation;
    }

    public Date getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(Date premiereDate) {
        this.premiereDate = premiereDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterFileName() {
        return posterFileName;
    }

    public void setPosterFileName(String posterFileName) {
        this.posterFileName = posterFileName;
    }

    public String getDateUploadFmt(){
        return new SimpleDateFormat("dd.MM.yyyy").format(dateUpload);
    }

    public String getPremiereDateFmt() {
        return new SimpleDateFormat("dd.MM.yyyy").format(premiereDate);
    }

    public String getTranslationStr() {
        return "TRANSLATION."+translation.toString();
    }

    public String getQualityStr() {
        return quality.getTitle();
    }
}
