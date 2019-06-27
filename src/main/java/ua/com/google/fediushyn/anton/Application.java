package ua.com.google.fediushyn.anton;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import ua.com.google.fediushyn.anton.exceptions.AddUserException;
import ua.com.google.fediushyn.anton.exceptions.FilmException;
import ua.com.google.fediushyn.anton.model.*;
import ua.com.google.fediushyn.anton.service.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(final UserService userService, final FilmCountryService filmCountryService,
                                  final FilmYearService filmYearService, final FilmGenreService filmGenreService,
                                  final FilmService filmService) {

        return strings -> {
            List<CustomUser> users = userService.findAll();
            if ((users == null) || (users.isEmpty())) {
                try {
                    userService.addUser("admin", "$2a$10$971scxofc0t83oBnZGauXOh1Gjb.j9kE4xbHoW2JJaJdiwzNfKgOW", true, UserRole.ADMIN, "", "", "");
                    userService.addUser("user", "$2a$10$971scxofc0t83oBnZGauXOh1Gjb.j9kE4xbHoW2JJaJdiwzNfKgOW", true, UserRole.USER, "", "", "");
                } catch (AddUserException e) {
                    e.printStackTrace();
                }
            }
            List<FilmCountry> filmCountries = filmCountryService.getFilmCounrties();
            if ((filmCountries == null) || (filmCountries.isEmpty())) {
                try {
                    filmCountryService.addFilmCountry("США", "usa");
                    filmCountryService.addFilmCountry("Франция", "france");
                    filmCountryService.addFilmCountry("Великобритания", "unitedkingdom");
                    filmCountryService.addFilmCountry("Россия", "russia");
                    filmCountryService.addFilmCountry("Украина", "urkaine");
                    filmCountryService.addFilmCountry("Германия", "germany");
                    filmCountryService.addFilmCountry("Индия", "india");
                    filmCountryService.addFilmCountry("СССР", "ussr");
                } catch (FilmException e){
                    e.printStackTrace();
                }
            }

            List<FilmYear> filmYears = filmYearService.getFilmYears();
            if ((filmYears == null) || (filmYears.isEmpty())) {
                try {
                    filmYearService.addFilmYear("2017");
                    filmYearService.addFilmYear("2016");
                    filmYearService.addFilmYear("2015");
                    filmYearService.addFilmYear("2014");
                    filmYearService.addFilmYear("2013");
                    filmYearService.addFilmYear("2012");
                    filmYearService.addFilmYear("2011");
                    filmYearService.addFilmYear("2010");
                } catch (FilmException e){
                    e.printStackTrace();
                }
            }

            List<FilmGenre> filmGenres = filmGenreService.getFilmGenres();
            if ((filmGenres == null) || (filmGenres.isEmpty())) {
                try {
                    filmGenreService.addFilmGenre("Комедия", "camedy");
                    filmGenreService.addFilmGenre("Вестерн", "western");
                    filmGenreService.addFilmGenre("Ужасы", "horrors");
                    filmGenreService.addFilmGenre("Биография", "biography");
                    filmGenreService.addFilmGenre("Боевик", "action");
                    filmGenreService.addFilmGenre("Военный", "military");
                    filmGenreService.addFilmGenre("Детектив", "detective");
                    filmGenreService.addFilmGenre("Детский", "childrens");
                    filmGenreService.addFilmGenre("Документальный", "documentary");
                    filmGenreService.addFilmGenre("Драма", "drama");
                    filmGenreService.addFilmGenre("Исторический", "historical");
                    filmGenreService.addFilmGenre("Криминал", "crime");
                    filmGenreService.addFilmGenre("Мелодрама", "melodrama");
                    filmGenreService.addFilmGenre("Мультфильм", "cartoon");
                    filmGenreService.addFilmGenre("Мюзикл", "musical");
                    filmGenreService.addFilmGenre("Приключения", "adventure");
                    filmGenreService.addFilmGenre("Семейный", "family");
                    filmGenreService.addFilmGenre("Спортивный", "sports");
                    filmGenreService.addFilmGenre("Триллер", "thriller");
                    filmGenreService.addFilmGenre("Фантастика", "fantastic");
                    filmGenreService.addFilmGenre("Фэнтези", "fantasy");
                } catch (FilmException e){
                    e.printStackTrace();
                }
            }

            List<Film> films = filmService.getFilms();
            if ((films == null) || (films.isEmpty())) {
                FilmCountry filmCountryAdd = filmCountryService.getFilmCountryByCode("usa");
                List<FilmGenre> filmGenresAdd = new ArrayList<>();
                filmGenresAdd.add(filmGenreService.getFilmGenreByCode("action"));
                filmGenresAdd.add(filmGenreService.getFilmGenreByCode("crime"));
                String cast = "Энсел Элгорт, Кевин Спейси, Лили Джеймс, Эйса Гонсалес, Джон Хэмм, Джейми Фокс, Джон Бернтал, Фли, Лэнни Джун, Мика Ховард";
                String director = "Эдгар Райт";
                String duration = "01:49:48";
                String notes = "Обычный парень из бедной семьи отчаянно пытается зацепиться в жизни за малейший шанс. Выбраться из низов не так-то просто. Однако протянул бы хоть кто-то веревку, по которой можно было начать делать первые шаги. Вскарабкиваться вверх и обеспечивать будущее. Но кому сдался паренек из трущоб. Однако нашелся среди равнодушных людей добрый человек.\n" + " \n" + " Им оказался гангстер. Работа на мафиози — не самое лучшее предприятие. Но на безрыбье и рак рыба. Поэтому не задавая лишних вопросов, главный герой выполняет мелкие поручения. Зачастую даже не нарушая при этом закон. И вот пришло время приступить к важному и ответственному заданию. Предстоит совершить ограбление. Устраивайтесь поудобнее и начинайте фильм Малыш на драйве смотреть онлайн бесплатно в хорошем качестве.";
                FilmQualities quality = FilmQualities.HDRIP;
                String name = "Малыш на драйве (2017)";
                String imagePath = "https://filmets.net/uploads/posts/2017-12/1512239538-575955256-malysh-na-drayve.jpg";
                String pathFilm = "/videosrc?fileName=Однажды.mp4";//"http://wwww.kinogo.cc/5400f8bc4964f686997d9e4a53c233eb:2019041818/filmzzz/chelovek-pauk-li-2017_1523949622.mp4";
                filmService.addFilm("Человек паук ", filmYearService.getFilmYear("2015"), filmCountryAdd,
                        notes, quality, duration, FilmTranslation.UNKNOWN, new Date(), imagePath, "", filmGenresAdd,
                        new FilmDetail(cast, director, pathFilm));
                for (int i = 0; i < 150; i++) {
                    Integer val = ThreadLocalRandom.current().nextInt(2010, 2018);
                    filmService.addFilm(name + " " + (i + 1), filmYearService.getFilmYear(val.toString()), filmCountryAdd,
                            notes, quality, duration, FilmTranslation.UNKNOWN, new Date(), imagePath, "", filmGenresAdd,
                            new FilmDetail(cast + (i + 1), director + (i + 1), pathFilm));
                }
            }
        };
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor loc = new LocaleChangeInterceptor();
        loc.setParamName("lang");
        return loc;
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }
}
