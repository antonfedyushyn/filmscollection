package ua.com.google.fediushyn.anton.contorllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.locale.LocaleMessages;
import ua.com.google.fediushyn.anton.model.*;
import ua.com.google.fediushyn.anton.service.*;

import java.util.*;

@Controller
public class AdminController {
    private FilmYearService filmYearService;
    private FilmCountryService filmCountryService;
    private FilmGenreService filmGenreService;
    private FilmService filmService;

    private MessageSource messageSource;

    private FilmsController filmsController;

    @Autowired
    AdminController(FilmYearService filmYearService,
                    FilmCountryService filmCountryService,
                    FilmGenreService filmGenreService,
                    FilmService filmService,
                    MessageSource messageSource,
                    FilmsController filmsController){
        this.filmYearService = filmYearService;
        this.filmCountryService = filmCountryService;
        this.filmGenreService = filmGenreService;
        this.filmService = filmService;
        this.messageSource = messageSource;
        this.filmsController = filmsController;
    }

    final private String[] params = {};

    private final static int pageSize = 10;

    private Map<String, String> getFilmTransaltions(){
        Map<String, String> hashMap = new LinkedHashMap<>();
        FilmTranslation[] enums = FilmTranslation.values();
        for (FilmTranslation enumVal: enums) {
            hashMap.put(enumVal.toString(), "TRANSLATION."+enumVal.toString());
        }
        return hashMap;
    }

    private Map<String, String> getFilmQualities(){
        Map<String, String> hashMap = new LinkedHashMap<>();
        FilmQualities[] enums = FilmQualities.values();
        for (FilmQualities enumVal: enums) {
            hashMap.put(enumVal.toString(), enumVal.getTitle());
        }
        return hashMap;
    }

    @GetMapping("/admin")
    public String admin(Model model){
        filmsController.setContext(model);
        return "admin";
    }

    @GetMapping("/addFilm")
    public String addFilm(Model model){
        filmsController.setContext(model);
        model.addAttribute("translationEnums", getFilmTransaltions());
        model.addAttribute("qualitiesEnums", getFilmQualities());
        return "addFilm";
    }

    @PostMapping(value = "/addNewFilm")
    public String setFilm(@RequestParam(name = "filmName") String filmName,
                          @RequestParam(name = "filmPath") String filmPath,
                          @RequestParam(name = "filmYear") String filmYear,
                          @RequestParam(name = "filmCountry") String filmCountry,
                          @RequestParam(name = "filmGenres[]") String[] filmGenres,
                          @RequestParam(name = "filmQuality", required = false, defaultValue = "UNKNOWN") String filmQuality,
                          @RequestParam(name = "filmDuration") String filmDuration,
                          @RequestParam(name = "filmNotes") String filmNotes,
                          @RequestParam(name = "filmDatailDirector") String filmDatailDirector,
                          @RequestParam(name = "filmDatailCast") String filmDatailCast,
                          @RequestParam(name = "posterFilePath") String posterFilePath,
                          @RequestParam(name = "posterFileName") String posterFileName,
                          @RequestParam(name = "filmTranslation", required = false, defaultValue = "UNKNOWN") String filmTranslation,
                          @RequestParam(name = "screenShoots", required = false, defaultValue = "") String filmScreenShoots,
                          Model model) {
        FilmYear filmYearAdd = filmYearService.getFilmYear(filmYear);
        FilmCountry filmCountryAdd = filmCountryService.getFilmCountryByCode(filmCountry);
        List<FilmGenre> filmGenresAdd = new ArrayList<>();
        for (String i: filmGenres) {
            if (filmGenreService.existsFilmGenreByCode(i)) {
                filmGenresAdd.add(filmGenreService.getFilmGenreByCode(i));
            }
        }
        FilmDetail filmDetail = new FilmDetail();
        filmDetail.setCast(filmDatailCast);
        filmDetail.setDirector(filmDatailDirector);
        filmDetail.setPathFilm(filmPath);
        List<String> imagePathes = new ArrayList<>(Arrays.asList(filmScreenShoots.split(",")));
        filmDetail.setImagesPathes(imagePathes);
        String resMessage = "";
        try {
            filmService.addFilm(filmName, filmYearAdd, filmCountryAdd, filmNotes, FilmQualities.toEnum(filmQuality), filmDuration, FilmTranslation.toEnum(filmTranslation), new Date(), posterFilePath, posterFileName, filmGenresAdd, filmDetail);

            filmsController.index(model);
            filmsController.setResultMessage(true, resMessage, model);
            return "redirect:/addFilm";
        } catch (FilmException e) {
            filmsController.setContext(model);
            resMessage = LocaleMessages.getLocaleMessage(messageSource, params, e.getMessage(), e.getMessageDefault(),
                    e.getValue());
            filmsController.setResultMessage(false, resMessage, model);
            model.addAttribute("filmName", filmName);
            model.addAttribute("filmPath", filmPath);
            model.addAttribute("filmYear", filmYear);
            model.addAttribute("filmCountry", filmCountry);
            model.addAttribute("filmGenres[]", filmGenres);
            model.addAttribute("filmQuality", filmQuality);
            model.addAttribute("filmDuration", filmDuration);
            model.addAttribute("filmNotes", filmNotes);
            model.addAttribute("filmDatailDirector", filmDatailDirector);
            model.addAttribute("filmDatailCast", filmDatailCast);
            model.addAttribute("posterFilePath", posterFilePath);
            model.addAttribute("posterFileName", posterFileName);
            model.addAttribute("filmTranslation", filmTranslation);
            model.addAttribute("screenShoots", filmScreenShoots);
            return "addFilm";
        }
    }

    @GetMapping("/addYear")
    public String showAddYear(Model model){
        filmsController.setContext(model);
        return "addYear";
    }
    @PostMapping(value = "/addYear")
    public String setYearFilm(@RequestParam(name = "filmYear") String filmYear,
                              Model model) {
        String resMessage = "";
        try{
            filmYearService.addFilmYear(filmYear);
            filmsController.setContext(model);
            filmsController.setResultMessage(true, resMessage, model);
            return "redirect:/addYear";
        } catch (FilmException e) {
            filmsController.setContext(model);
            resMessage = LocaleMessages.getLocaleMessage(messageSource, params, e.getMessage(), e.getMessageDefault(),
                    e.getValue());
            filmsController.setResultMessage(false, resMessage, model);
            model.addAttribute("filmYear", filmYear);
            return "addYear";
        }
    }

    @GetMapping("/addGenre")
    public String showAddGenre(Model model){
        filmsController.setContext(model);
        return "addGenre";
    }
    @PostMapping(value = "/addGenre")
    public String setGenreFilm(@RequestParam(name = "genreName") String genreName,
                               @RequestParam(name = "genreCode") String genreCode,
                               Model model) {
        String resMessage = "";
        try{
            filmGenreService.addFilmGenre(genreName, genreCode);
            filmsController.setContext(model);
            filmsController.setResultMessage(true, resMessage, model);
            return "redirect:/addGenre";
        } catch (FilmException e) {
            filmsController.setContext(model);
            resMessage = LocaleMessages.getLocaleMessage(messageSource, params, e.getMessage(), e.getMessageDefault(),
                    e.getValue());
            filmsController.setResultMessage(false, resMessage, model);
            model.addAttribute("genreName", genreName);
            model.addAttribute("genreCode", genreCode);
            return "addGenre";
        }
    }

    @GetMapping("/addCountry")
    public String showAddCountry(Model model){
        filmsController.setContext(model);
        return "addCountry";
    }
    @PostMapping(value = "/addCountry")
    public String setCountryFilm(@RequestParam(name = "countryName") String countryName,
                                 @RequestParam(name = "countryCode") String countryCode,
                                 Model model) {
        String resMessage = "";
        try {
            filmCountryService.addFilmCountry(countryName, countryCode);
            filmsController.setContext(model);
            filmsController.setResultMessage(true, resMessage, model);
            return "redirect:/addCountry";
        } catch (FilmException e) {
            filmsController.setContext(model);
            resMessage = LocaleMessages.getLocaleMessage(messageSource, params, e.getMessage(), e.getMessageDefault(),
                    e.getValue());
            filmsController.setResultMessage(false, resMessage, model);
            model.addAttribute("countryName", countryName);
            model.addAttribute("countryCode", countryCode);
            return "addCountry";
        }
    }
}
