package com.perscholas.astrodia.services;

import com.perscholas.astrodia.dto.UserDto;
import com.perscholas.astrodia.models.User;
import com.perscholas.astrodia.repositories.UserRepository;
import com.perscholas.astrodia.validators.UserAlreadyExistException;
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


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
//        user.setRoles(Arrays.asList("ROLE_USER"));
        user.setRole("USER");
        userRepository.save(user);
        return user;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
