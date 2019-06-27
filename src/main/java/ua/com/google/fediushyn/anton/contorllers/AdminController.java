package ua.com.google.fediushyn.anton.contorllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.locale.LocaleMessages;
import ua.com.google.fediushyn.anton.model.*;
import ua.com.google.fediushyn.anton.service.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {
    private final FilmYearService filmYearService;
    private final FilmCountryService filmCountryService;
    private final FilmGenreService filmGenreService;
    private final FilmService filmService;


    private final FilmsController filmsController;

    @Autowired
    AdminController(FilmYearService filmYearService,
                    FilmCountryService filmCountryService,
                    FilmGenreService filmGenreService,
                    FilmService filmService,
                    FilmsController filmsController){
        this.filmYearService = filmYearService;
        this.filmCountryService = filmCountryService;
        this.filmGenreService = filmGenreService;
        this.filmService = filmService;
        this.filmsController = filmsController;
    }

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
                          @RequestParam(name = "premiereDate", required = false, defaultValue = "") String premiereDateStr,
                          Model model) {
        FilmYear filmYearAdd = filmYearService.getFilmYear(filmYear);
        FilmCountry filmCountryAdd = filmCountryService.getFilmCountryByCode(filmCountry);
        List<FilmGenre> filmGenresAdd = new ArrayList<>();
        if (filmGenres.length > 0) {
            for (String i : filmGenres) {
                if (filmGenreService.existsFilmGenreByCode(i)) {
                    filmGenresAdd.add(filmGenreService.getFilmGenreByCode(i));
                }
            }
        }
        FilmDetail filmDetail = new FilmDetail();
        filmDetail.setCast(filmDatailCast);
        filmDetail.setDirector(filmDatailDirector);
        filmDetail.setPathFilm(filmPath);
        List<String> imagePathes = new ArrayList<>(Arrays.asList(filmScreenShoots.split(",")));
        filmDetail.setImagesPathes(imagePathes);
        String resMessage = "";
        Date premiereDate;
        if (premiereDateStr.isEmpty()) {
            premiereDate = null;
        } else {
            DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
            try {
                premiereDate = format.parse(premiereDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                premiereDate = null;
            }
        }
        try {
            filmService.addFilm(filmName, filmYearAdd, filmCountryAdd, filmNotes.trim(), FilmQualities.toEnum(filmQuality), filmDuration, FilmTranslation.toEnum(filmTranslation), premiereDate, posterFilePath, posterFileName, filmGenresAdd, filmDetail);

            filmsController.index(model);
            filmsController.setResultMessage(true, resMessage, model);
            return "redirect:/addFilm";
        } catch (FilmException e) {
            filmsController.setContext(model);
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getMessageDefault(),
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
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getMessageDefault(),
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
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getMessageDefault(),
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
            resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getMessageDefault(),
                    e.getValue());
            filmsController.setResultMessage(false, resMessage, model);
            model.addAttribute("countryName", countryName);
            model.addAttribute("countryCode", countryCode);
            return "addCountry";
        }
    }

    @GetMapping("/editFilm")
    public String showEditFilm(@RequestParam(name = "code") String code,
                               Model model) {
        filmsController.setContext(model);
        Film film = filmService.getFilmByCode(code);
        Boolean result = true;
        String resMessage = "";
        if (film == null) {
            result = false;
            resMessage = filmsController.getLocaleMessage("Film.not.found", "Movie not found!");
        } else {
            model.addAttribute("film", film);
        }
        filmsController.setResultMessage(result, resMessage, model);
        model.addAttribute("translationEnums", getFilmTransaltions());
        model.addAttribute("qualitiesEnums", getFilmQualities());
        return "editFilm";
    }

    @PostMapping("/modifyFilm")
    public String modifyFilm(@RequestParam(name = "filmName") String filmName,
                             @RequestParam(name = "filmCode") String filmCode,
                             @RequestParam(name = "filmId") String filmId,
                             @RequestParam(name = "filmPath") String filmPath,
                             @RequestParam(name = "filmYear") String filmYear,
                             @RequestParam(name = "filmCountry") String filmCountry,
                             @RequestParam(name = "filmGenres[]", required = false) String[] filmGenres,
                             @RequestParam(name = "filmQuality", required = false, defaultValue = "UNKNOWN") String filmQuality,
                             @RequestParam(name = "filmDuration") String filmDuration,
                             @RequestParam(name = "filmNotes") String filmNotes,
                             @RequestParam(name = "filmDatailDirector") String filmDatailDirector,
                             @RequestParam(name = "filmDatailCast") String filmDatailCast,
                             @RequestParam(name = "posterFilePath") String posterFilePath,
                             @RequestParam(name = "posterFileName") String posterFileName,
                             @RequestParam(name = "filmTranslation", required = false, defaultValue = "UNKNOWN") String filmTranslation,
                             @RequestParam(name = "screenShoots", required = false, defaultValue = "") String filmScreenShoots,
                             @RequestParam(name = "premiereDate", required = false, defaultValue = "") String premiereDateStr,
                             Model model){
        Film film = filmService.getFilmByCode(filmCode);
        if (film != null) {
            FilmYear filmYearAdd = filmYearService.getFilmYear(filmYear);
            FilmCountry filmCountryAdd = filmCountryService.getFilmCountryByCode(filmCountry);
            film.setYear(filmYearAdd);
            film.setCountry(filmCountryAdd);
            List<FilmGenre> filmGenresNew = new ArrayList<>();
            if (filmGenres != null) {
                for (String i : filmGenres) {
                    if (filmGenreService.existsFilmGenreByCode(i)) {
                        filmGenresNew.add(filmGenreService.getFilmGenreByCode(i));
                    }
                }
            }
            List<FilmGenre> filmGenresOld = film.getFilmGenres();
            if (filmGenres != null){
                List<FilmGenre> filmGenresRemove = new ArrayList<>();
                for (FilmGenre genre: filmGenresOld) {
                    if (!filmGenresNew.contains(genre)){
                        filmGenresRemove.add(genre);
                    }
                }
                for (FilmGenre genre: filmGenresRemove) {
                    film.removeGenre(genre);
                }
                for (FilmGenre genre: filmGenresNew) {
                    film.addGenre(genre);
                }
            } else {
                film.removeAllGenres();
            }
            film.setName(filmName);
            film.setQuality(FilmQualities.toEnum(filmQuality));
            film.setDuration(filmDuration);
            film.setNotes(filmNotes.trim());
            film.setPosterUrl(posterFilePath);
            film.setPosterFileName(posterFileName);
            film.setTranslation(FilmTranslation.toEnum(filmTranslation));
            Date premiereDate;
            if (premiereDateStr.isEmpty()) {
                premiereDate = null;
            } else {
                DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
                try {
                    premiereDate = format.parse(premiereDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    premiereDate = null;
                }
            }
            film.setPremiereDate(premiereDate);
            List<String> imagePathes = new ArrayList<>(Arrays.asList(filmScreenShoots.split(",")));
            film.getFilmDetail().setImagesPathes(imagePathes);
            film.getFilmDetail().setDirector(filmDatailDirector);
            film.getFilmDetail().setCast(filmDatailCast);
            film.getFilmDetail().setPathFilm(filmPath);
            filmService.saveFilm(film);
            return filmsController.getFilm(filmCode, model);
        } else {
            filmsController.setContext(model);
            String resMessage = filmsController.getLocaleMessage("Films.not.found", "Movies not found!");
            filmsController.setResultMessage(false, resMessage, model);
            return "editFilm";
        }
    }

    @PostMapping(value = "/removeFilm")
    @ResponseBody
    public String setCountryFilm(@RequestParam(name = "filmCode") String filmCode) {
        Boolean result = true;
        String resMessage = "";
        Film film = filmService.getFilmByCode(filmCode);
        if ((film != null) && (film.getId() > 0)){
            try {
                filmService.deleteFilmByID(film.getId());
            } catch (FilmException e) {
                e.printStackTrace();
                result = false;
                resMessage = filmsController.getLocaleMessage(e.getMessage(), e.getMessageDefault());
            }
        }
        JSONObject json = new JSONObject();
        try{
            json.put("result", result);
            json.put("message", resMessage);
            resMessage = json.toString();
        } catch (JSONException e){
            if (result) {
                resMessage = "ok";
            } else {
                resMessage = "error";
            }
        }
        return resMessage;
    }
}
