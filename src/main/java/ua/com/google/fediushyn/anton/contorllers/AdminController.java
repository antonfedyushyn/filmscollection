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
    private MessageSource messageSource;

    @Autowired
    private FilmsController filmsController;

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
        List<FilmGenre> filmGenresAdd = new ArrayList<FilmGenre>();
        for (String i: filmGenres) {
            if (filmGenreService.existsFilmGenreByCode(i)) {
                filmGenresAdd.add(filmGenreService.getFilmGenreByCode(i));
            }
        }
        FilmDetail filmDetail = new FilmDetail();
        filmDetail.setCast(filmDatailCast);
        filmDetail.setDirector(filmDatailDirector);
        filmDetail.setPathFilm(filmPath);
        List<String> imagePathes = new ArrayList<String>(Arrays.asList(filmScreenShoots.split(",")));
        filmDetail.setImagesPathes(imagePathes);
        try {
            filmService.addFilm(filmName, filmYearAdd, filmCountryAdd, filmNotes, FilmQualities.toEnum(filmQuality), filmDuration, FilmTranslation.toEnum(filmTranslation), new Date(), posterFilePath, posterFileName, filmGenresAdd, filmDetail);

            filmsController.index(model);
            model.addAttribute("result", true);
            return "addFilm";
        } catch (FilmException e) {
            filmsController.setContext(model);
            model.addAttribute("result", false);
            String resMessage = LocaleMessages.getLocaleMessage(messageSource, params, e.getMessage(), e.getMessageDefault(),
                    e.getValue());
            model.addAttribute("resultMessage", resMessage);
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
        try{
            filmYearService.addFilmYear(filmYear);
            filmsController.setContext(model);
            model.addAttribute("result", true);
            return "addYear";
        } catch (FilmException e) {
            filmsController.setContext(model);
            model.addAttribute("result", false);
            String resMessage = LocaleMessages.getLocaleMessage(messageSource, params, e.getMessage(), e.getMessageDefault(),
                    e.getValue());
            model.addAttribute("resultMessage", resMessage);
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
        try{
            filmGenreService.addFilmGenre(genreName, genreCode);
            filmsController.setContext(model);
            model.addAttribute("result", true);
            return "addGenre";
        } catch (FilmException e) {
            filmsController.setContext(model);
            model.addAttribute("result", false);
            String resMessage = LocaleMessages.getLocaleMessage(messageSource, params, e.getMessage(), e.getMessageDefault(),
                    e.getValue());
            model.addAttribute("resultMessage", resMessage);
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
        try {
            filmCountryService.addFilmCountry(countryName, countryCode);
            filmsController.setContext(model);
            model.addAttribute("result", true);
            return "addCountry";
        } catch (FilmException e) {
            filmsController.setContext(model);
            model.addAttribute("result", false);
            String resMessage = LocaleMessages.getLocaleMessage(messageSource, params, e.getMessage(), e.getMessageDefault(),
                    e.getValue());
            model.addAttribute("resultMessage", resMessage);
            return "addCountry";
        }
    }
}
