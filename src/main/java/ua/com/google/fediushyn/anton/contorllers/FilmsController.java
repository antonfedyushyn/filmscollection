package ua.com.google.fediushyn.anton.contorllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.google.fediushyn.anton.exceptions.AddUserException;
import ua.com.google.fediushyn.anton.model.*;
import ua.com.google.fediushyn.anton.service.*;

import java.util.List;
import java.util.Locale;

@Controller
public class FilmsController {
    @Autowired
    private UserService userService;
    @Autowired
    private FilmYearService filmYearService;
    @Autowired
    private FilmCountryService filmCountryService;
    @Autowired
    private FilmGenreService filmGenreService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmCommentService filmCommentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private MessageSource messageSource;

    final private String[] params = {};

    private final static int pageSize = 10;

    public String getLocaleMessage(String message, String messageDefault){
        String returnMessage = "";
        try {
            Locale loc = LocaleContextHolder.getLocale();
            returnMessage = messageSource.getMessage(message, params, loc);
        } catch (NoSuchMessageException e) {
            returnMessage = messageDefault;
        }
        return returnMessage;
    }

    public String getLocaleMessage(String message, String messageDefault, String fileName){
        String returnMessage = "";
        try {
            Locale loc = LocaleContextHolder.getLocale();
            returnMessage = messageSource.getMessage(message, params, loc);
        } catch (NoSuchMessageException e) {
            returnMessage = messageDefault;
        }
        if (!fileName.isEmpty()) {
            returnMessage = String.format(returnMessage, fileName);
        }
        return returnMessage;
    }

    public void setUserData(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            String login = user.getUsername();
            if (!login.isEmpty()) {
                CustomUser dbUser = userService.findByLogin(login);
                model.addAttribute("isRegistration", true);
                model.addAttribute("login", login);
                model.addAttribute("roles", user.getAuthorities());
            } else {
                model.addAttribute("isRegistration", false);
            }
        } else {
            model.addAttribute("isRegistration", false);
        }
    }

    public void setFilmYears(Model model) {
        List<FilmYear> filmYears = filmYearService.getFilmYears();
        model.addAttribute("years", filmYears);
    }

    public void setFilmCountries(Model model){
        List<FilmCountry> filmCountries= filmCountryService.getFilmCounrties();
        model.addAttribute("countries", filmCountries);
    }

    public void setFilmGenres(Model model) {
        List<FilmGenre> filmGenres = filmGenreService.getFilmGenres();
        model.addAttribute("genres", filmGenres);
        model.addAttribute("genres_count", filmGenres.size());
    }

    public void setFilms(int page, String url, Page<Film> films, Model model, Boolean isFirstParam) {
        model.addAttribute("films", films.getContent());
        model.addAttribute("filmsPages", FilmPage.generatePagesList(films.getTotalPages(), page, url, isFirstParam));
        model.addAttribute("filmPageNo", page);
        model.addAttribute("filmCountPages", films.getTotalPages());

    }

    public void setFilms(int page, Model model){
        Pageable pageFilms = PageRequest.of(page, pageSize);
        Page<Film> films = filmService.getAllFilms(pageFilms);
        setFilms(page, "/index", films, model, true);
        Boolean result = true;
        String resMessage = "";
        if (films.getTotalElements() == 0) {
            result = false;
            resMessage = getLocaleMessage("Films.not.found", "Movies not found!");
        }
        setResultMessage(result, resMessage, model);
    }

    public void setContext(Model model){
        setUserData(model);
        setFilmYears(model);
        setFilmCountries(model);
        setFilmGenres(model);
    }

    public void setResultMessage(Boolean result, String resMessage, Model model){
        model.addAttribute("result", result);
        model.addAttribute("resMessage", resMessage);
    }

    @GetMapping("/")
    public String index(Model model){
        setContext(model);
        setFilms(0, model);
        return "index";
    }

    @GetMapping("/index")
    public String index1(@RequestParam(required = false, name = "page", defaultValue = "0") int page,
                         Model model){
        setContext(model);
        setFilms(page, model);
        return "index";
    }

    @PostMapping(value = "/update")
    public String update(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone) {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String login = user.getUsername();
        userService.updateUser(login, name, email, phone);

        return "redirect:/";
    }

    @PostMapping(value = "/register")
    public String update(@RequestParam(name = "login") String login,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "password_confirm") String passwordConfirm,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         Model model) {
        String passHash = passwordEncoder.encode(password);

        try {
            userService.addUser(login, passHash, password.equals(passwordConfirm), UserRole.USER, name, email, phone);
            securityService.autoLogin(login, password);
            return "redirect:/";
        } catch (AddUserException e) {
            model.addAttribute("login", login);
            model.addAttribute("email", email);
            model.addAttribute("result", false);
            String resMessage = getLocaleMessage(e.getMessage(), e.getMessageDefault(), e.getValue());
            model.addAttribute("resultMessage", resMessage);
            setContext(model);
            return "register";
        }
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/user/{login}")
    public String user(@PathVariable(value = "login") String userLogin){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String url = "redirect:/";
        if (principal instanceof User) {
            User user = (User) principal;
            String login = user.getUsername();
            if (userLogin.equals(login)) {
               CustomUser dbUser = userService.findByLogin(login);
               switch (dbUser.getRole()){
                   case USER:
                       url = "redirect:/";
                       break;
                   case ADMIN:
                       url = "redirect:/admin";
                       break;
                   default:
                       url = "redirect:/";
               }
            } else {
                url = "redirect:/";
            }
        } else {
            url = "redirect: /";
        }
        return url;
    }

    @GetMapping(value = "/register")
    public String register(Model model){
        setContext(model);
        return "register";
    }



    @RequestMapping(value = "/film", method = RequestMethod.GET)
    public String getFilm(@RequestParam(name = "code") String code,
                          Model model) {
        setContext(model);
        Film film = filmService.getFilmByCode(code);
        Boolean result = true;
        String resMessage = "";
        if (film == null) {
            result = false;
            resMessage = getLocaleMessage("Film.not.found", "Movie not found!");
        } else {
            model.addAttribute("film", film);
        }
        setResultMessage(result, resMessage, model);
        return "film";
    }

    @RequestMapping(value = "/genre", method = RequestMethod.GET)
    public String getFilmsByGenre(@RequestParam(name = "code") String code,
                                  @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                  Model model) {
        Pageable pageFilms = PageRequest.of(page, pageSize);
        FilmGenre filmGenre = filmGenreService.getFilmGenreByCode(code);
        Page<Film> films = filmGenre.getFilms(pageFilms);
        setContext(model);
        setFilms(page, "/genre?code="+code, films, model, false);
        Boolean result = true;
        String resMessage = "";
        if (films.getTotalElements() == 0) {
            result = false;
            resMessage = getLocaleMessage("Films.catecory.not.found", "There are no movies in the selected category!");
        }
        setResultMessage(result, resMessage, model);
        return "index";
    }

    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public String getFilmsByCountry(@RequestParam(name = "code") String code,
                                    @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                    Model model) {
        Pageable pageFilms = PageRequest.of(page, pageSize);
        FilmCountry filmCountry = filmCountryService.getFilmCountryByCode(code);
        Page<Film> films = filmCountry.getFilms(pageFilms);
        setContext(model);
        setFilms(page, "/country?code="+code, films, model, false);
        Boolean result = true;
        String resMessage = "";
        if (films.getTotalElements() == 0) {
            result = false;
            resMessage = getLocaleMessage("Films.catecory.not.found", "There are no movies in the selected category!");
        }
        setResultMessage(result, resMessage, model);
        return "index";
    }

    @RequestMapping(value = "/year", method = RequestMethod.GET)
    public String getFilmsByYear(@RequestParam(name = "code") String code,
                                 @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                 Model model) {
        Pageable pageFilms = PageRequest.of(page, pageSize);
        FilmYear filmYear = filmYearService.getFilmYear(code);
        Page<Film> films = filmYear.getFilms(pageFilms);
        setContext(model);
        setFilms(page, "/year?code="+code, films, model, false);
        Boolean result = true;
        String resMessage = "";
        if (films.getTotalElements() == 0) {
            result = false;
            resMessage = getLocaleMessage("Films.catecory.not.found", "There are no movies in the selected category!");
        }
        setResultMessage(result, resMessage, model);
        return "index";
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String getFilmsByNameOrNotes(@RequestParam(name = "findText") String findText,
                                        Model model) {
        Pageable pageFilms = PageRequest.of(0, pageSize);
        Page<Film> films = filmService.getFilmsByName(findText, pageFilms);
        setContext(model);
        setFilms(0, "/find?findText="+findText, films, model, false);
        Boolean result = true;
        String resMessage = "";
        if (films.getTotalElements() == 0) {
            result = false;
            resMessage = getLocaleMessage("Film.not.found", "Movie not found!");
        }
        setResultMessage(result, resMessage, model);
        return "index";
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String getFilmsByNameOrNotes(@RequestParam(name = "findText") String findText,
                                        @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                        Model model) {
        Pageable pageFilms = PageRequest.of(page, pageSize);
        Page<Film> films = filmService.getFilmsByName(findText, pageFilms);
        setContext(model);
        setFilms(page, "/find?findText="+findText, films, model, false);
        Boolean result = true;
        String resMessage = "";
        if (films.getTotalElements() == 0) {
            result = false;
            resMessage = getLocaleMessage("Film.not.found", "Movie not found!");
        }
        setResultMessage(result, resMessage, model);
        return "index";
    }

    @RequestMapping(value = "/film")
    public String getFilms(Model model) {
        return "redirect:/";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(Model model){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }
}
