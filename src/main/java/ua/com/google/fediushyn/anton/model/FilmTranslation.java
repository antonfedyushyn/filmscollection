package ua.com.google.fediushyn.anton.model;

public enum FilmTranslation {
    UNKNOWN, ORIGINAL, DUBBED, VOICEOVERMONO, VOICEOVERPOLY, SUBTITLE;

    public static FilmTranslation toEnum(String value){
        FilmTranslation filmTranslation;
        try {
            filmTranslation = FilmTranslation.valueOf(value);
        } catch (IllegalArgumentException e) {
            filmTranslation = UNKNOWN;
        }
        return filmTranslation;
    }

    @Override
    public String toString(){
        return name();
    }
}
