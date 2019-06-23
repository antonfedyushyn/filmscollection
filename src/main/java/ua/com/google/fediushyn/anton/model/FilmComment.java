package ua.com.google.fediushyn.anton.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "film_comments")
public class FilmComment {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_film")
    private Film film;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private CustomUser user;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "dt_comment", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtComment;

    @Column(name = "dt_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCommentUpdate;

    public FilmComment() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDtComment() {
        return dtComment;
    }

    public void setDtComment(Date dtComment) {
        this.dtComment = dtComment;
    }

    public Date getDtCommentUpdate() {
        return dtCommentUpdate;
    }

    public void setDtCommentUpdate(Date dtCommentUpdate) {
        this.dtCommentUpdate = dtCommentUpdate;
    }
}
