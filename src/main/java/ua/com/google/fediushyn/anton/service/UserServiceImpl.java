package ua.com.google.fediushyn.anton.service;

import ua.com.google.fediushyn.anton.exceptions.AddUserException;
import ua.com.google.fediushyn.anton.model.CustomUser;
import ua.com.google.fediushyn.anton.model.UserRole;
import ua.com.google.fediushyn.anton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public List<CustomUser> findAll() { return userRepository.findAll(); }

    @Transactional
    public void addUser(String login, String passHash, boolean equalsPassword, UserRole role, String name, String email,
                        String phone) throws AddUserException {
        if (userRepository.existsByLogin(login)) {
            throw new AddUserException("Duplicate.userForm.username", "Login exists in system!");
        }

        if (!equalsPassword) {
            throw new AddUserException("Different.userForm.password", "Passwords don't match!");
        }

        CustomUser user = new CustomUser(login, passHash, role, name, email, phone);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(String login, String name, String email, String phone) {
        if (userRepository.existsByLogin(login)) {
            CustomUser user = userRepository.findByLogin(login);

            user.setEmail(email);
            user.setPhone(phone);
            user.setName(name);

            userRepository.save(user);
        }
    }
}
