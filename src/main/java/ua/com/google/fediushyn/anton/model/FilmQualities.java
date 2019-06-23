package ua.com.google.fediushyn.anton.model;

public enum FilmQualities {
    UNKNOWN ("unknown"),
    CAMRIP ("CAMRip"),
    TS ("TS"),
    DVDSCR ("DVDScr"),
    DVDRIP ("DVDRip"),
    TVRIP ("TVRip"),
    SATRIP ("SATRip"),
    DVBRIP ("DVBRip"),
    IPTVRIP ("IPTVRip"),
    DVD5 ("DVD5"),
    DVD9 ("DVD9"),
    DBRIP ("BDRip"),
    VHSRIP ("VHSRip"),
    HDRIP ("HDRip");

    FilmQualities() {
    }

    FilmQualities(String title){
        this.title = title;
    }

    public static FilmQualities toEnum(String value){
        FilmQualities filmQualities;
        try {
            filmQualities = FilmQualities.valueOf(value);
        } catch (IllegalArgumentException e) {
            filmQualities = UNKNOWN;
        }
        return filmQualities;
    }

    private String title;

    @Override
    public String toString(){
        return name();
    }

    public String getTitle() {
        return title;
    }
}
