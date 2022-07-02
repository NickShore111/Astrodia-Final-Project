package org.nicholasshore.astrodia.services;

import org.nicholasshore.astrodia.dto.UserDto;
import org.nicholasshore.astrodia.models.AuthGroup;
import org.nicholasshore.astrodia.models.User;
import org.nicholasshore.astrodia.repositories.AuthGroupRepository;
import org.nicholasshore.astrodia.repositories.UserRepository;
import org.nicholasshore.astrodia.validators.UserAlreadyExistException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class UserService {
    UserRepository userRepository;
    AuthGroupRepository authRepo;

    @Autowired
    public UserService(UserRepository userRepository, AuthGroupRepository authRepo) {
        this.userRepository = userRepository;
        this.authRepo = authRepo;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(User u) {
        userRepository.delete(u);
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public User findByEmail(String email) throws NoSuchElementException{
        return userRepository.findByEmail(email);
    }

    public void saveOrUpdate(User u) {
        userRepository.save(u);
    }

    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail().toLowerCase());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        userRepository.save(user);
        authRepo.save(new AuthGroup(userDto.getEmail(), "ROLE_USER"));
        return user;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
