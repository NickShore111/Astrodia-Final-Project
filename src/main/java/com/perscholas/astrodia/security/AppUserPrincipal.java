package com.perscholas.astrodia.security;

import com.perscholas.astrodia.models.AuthGroup;
import com.perscholas.astrodia.models.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppUserPrincipal implements UserDetails {

    User user;
    List<AuthGroup> authGroup;

    public AppUserPrincipal(User user, List<AuthGroup> authGroup) {
        this.user = user;
        this.authGroup = authGroup;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // check if list is empty
        if(authGroup == null) return Collections.emptyList();
        // init a Set to disallow duplications
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        // loop through authGroup list and adding each role to spring security Simple Granted Authority object
        authGroup.forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth.getAAuthGroup())));
        // return the authorities wrapped in SimpleGrantedAuthority
        return authorities;
    }

    @Override
    public String getPassword() throws NullPointerException {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
