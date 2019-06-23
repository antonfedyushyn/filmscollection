package ua.com.google.fediushyn.anton.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.google.fediushyn.anton.repositories.FilmCommentRepository;

@Service
public class FilmCommentService {
    @Autowired
    private FilmCommentRepository filmCommentRepository;


}
