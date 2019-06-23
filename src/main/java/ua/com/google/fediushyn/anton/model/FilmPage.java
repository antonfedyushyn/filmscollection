package ua.com.google.fediushyn.anton.model;

import java.util.ArrayList;
import java.util.List;

public class FilmPage {
    public static enum TypePage{
        FIRSTPAGE,
        PREVIOUSPAGE,
        PAGE,
        NEXTPAGE,
        LASTPAGE;
    }
    private int number;
    private Boolean isActive;
    private String name;
    private String url;

    private TypePage typePage;

    public FilmPage() {}

    public FilmPage(Boolean isActive, int number, String url, TypePage typePage) {
        this.number = number;
        this.name = new Integer(number+1).toString();
        this.url = url;
        this.isActive = isActive;
        this.typePage = typePage;
    }

    public FilmPage(Boolean isActive, int number, String name, String url, TypePage typePage) {
        this.number = number;
        this.name = name;
        this.url = url;
        this.isActive = isActive;
        this.typePage = typePage;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean IsActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TypePage getTypePage() {
        return typePage;
    }

    public void setTypePage(TypePage typePage) {
        this.typePage = typePage;
    }

    public static List<FilmPage> generatePagesList(int countPages, int currentPage, String urlPref, Boolean isFirstParam) {
        List<FilmPage> filmPages = new ArrayList<FilmPage>();
        String url = urlPref;
        if (!isFirstParam) {
            url += "&";
        } else {
            url += "?";
        }
        if (countPages > 1) {
            if (currentPage == 0) {
                filmPages.add(new FilmPage(false, -1, "First", url, FilmPage.TypePage.FIRSTPAGE));
                filmPages.add(new FilmPage(false, -1, "Previous", url, FilmPage.TypePage.PREVIOUSPAGE));
            } else {
                filmPages.add(new FilmPage(true, -1, "First", url+"page=0", FilmPage.TypePage.FIRSTPAGE));
                filmPages.add(new FilmPage(true, -1, "Previous", url+"page="+(currentPage-1), FilmPage.TypePage.PREVIOUSPAGE));
            }
            if (countPages < 11) {
                for (int i = 0; i < countPages; i++) {
                    if (i == currentPage) {
                        filmPages.add(new FilmPage(false, i, url, FilmPage.TypePage.PAGE));
                    } else {
                        filmPages.add(new FilmPage(true, i, url+"page=" + i, FilmPage.TypePage.PAGE));
                    }
                }
            }
            else {
                if ((currentPage > 5) && (countPages - currentPage > 5)) {
                    filmPages.add(new FilmPage(true, 0, url, FilmPage.TypePage.PAGE));
                    filmPages.add(new FilmPage(false, -1, "...", url, FilmPage.TypePage.PAGE));
                    for (int i = currentPage - 4; i < currentPage + 4; i++) {
                        if (i == currentPage) {
                            filmPages.add(new FilmPage(false, i, url, FilmPage.TypePage.PAGE));
                        } else {
                            filmPages.add(new FilmPage(true, i, url+"page="+i, FilmPage.TypePage.PAGE));
                        }
                    }
                    filmPages.add(new FilmPage(false, -1, "...", url, FilmPage.TypePage.PAGE));
                    filmPages.add(new FilmPage(true, countPages-1, url+"page="+(countPages-1), FilmPage.TypePage.PAGE));
                } else {
                    if ((currentPage <= 5)) {
                        for (int i = 0; i < 10; i++) {
                            if (i == currentPage) {
                                filmPages.add(new FilmPage(false, i, url, FilmPage.TypePage.PAGE));
                            } else {
                                filmPages.add(new FilmPage(true, i, url+"page="+i, FilmPage.TypePage.PAGE));
                            }
                        }
                        filmPages.add(new FilmPage(false, -1, "...", url, FilmPage.TypePage.PAGE));
                        filmPages.add(new FilmPage(true, countPages - 1, url+"page="+(countPages-1), FilmPage.TypePage.PAGE));
                    } else {
                        filmPages.add(new FilmPage(true, 0, url+"page=0", FilmPage.TypePage.PAGE));
                        filmPages.add(new FilmPage(false, -1, "...", url, FilmPage.TypePage.PAGE));
                        for (int i = countPages-9; i < countPages; i++) {
                            if (i == currentPage) {
                                filmPages.add(new FilmPage(false, i, url, FilmPage.TypePage.PAGE));
                            } else {
                                filmPages.add(new FilmPage(true, i, url+"page="+i, FilmPage.TypePage.PAGE));
                            }
                        }
                    }
                }
            }
            if (currentPage == countPages-1) {
                filmPages.add(new FilmPage(false, -1, "Next", url+"page="+(countPages-1), FilmPage.TypePage.NEXTPAGE));
                filmPages.add(new FilmPage(false, -1, "Last", url+"page="+(countPages-1), FilmPage.TypePage.LASTPAGE));
            } else {
                filmPages.add(new FilmPage(true, -1, "Next", url+"page="+(currentPage+1), FilmPage.TypePage.NEXTPAGE));
                filmPages.add(new FilmPage(true, -1, "Last", url+"page="+(countPages-1), FilmPage.TypePage.LASTPAGE));
            }
        }
        return filmPages;
    }

    public static List<FilmPage> generatePagesList(int countPages, int currentPage, String urlPref){
        return generatePagesList(countPages, currentPage, urlPref, false);
    }
}
