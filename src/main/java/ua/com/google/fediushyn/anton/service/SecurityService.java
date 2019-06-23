package ua.com.google.fediushyn.anton.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
