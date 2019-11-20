package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.AddUserException;
import ua.com.google.fediushyn.anton.model.CustomUser;
import ua.com.google.fediushyn.anton.model.UserRole;

import java.util.List;

public interface UserService {

    CustomUser findByLogin(String login);

    List<CustomUser> findAll();

    void addUser(String login, String passHash, boolean equalsPassword, UserRole role, String name, String email,
                           String phone) throws AddUserException;

    void updateUser(String login, String name, String email, String phone);
}
