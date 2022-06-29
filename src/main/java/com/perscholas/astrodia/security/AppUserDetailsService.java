package com.perscholas.astrodia.security;

import com.perscholas.astrodia.models.AuthGroup;
import com.perscholas.astrodia.models.User;
import com.perscholas.astrodia.repositories.AuthGroupRepository;
import com.perscholas.astrodia.services.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AppUserDetailsService implements UserDetailsService {

    AuthGroupRepository authGroupRepository;
    UserService userService;
    @Autowired
    public AppUserDetailsService(AuthGroupRepository authGroupRepository, UserService userService) {
        this.authGroupRepository = authGroupRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.findByEmail(username);
        } catch (Exception ex){
            log.warn("Couldn't find email: " + username);
            ex.printStackTrace();
        }
        List<AuthGroup> authGroups = authGroupRepository.findByaEmail(username);
        return new AppUserPrincipal(user, authGroups);
    }
}
