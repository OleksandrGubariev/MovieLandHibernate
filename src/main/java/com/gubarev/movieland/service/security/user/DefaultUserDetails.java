package com.gubarev.movieland.service.security.user;

import com.gubarev.movieland.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class DefaultUserDetails implements UserDetails {
    private long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    static DefaultUserDetails fromUserEntityToCustomUserDetails(User user) {
        DefaultUserDetails defaultUserDetails = new DefaultUserDetails();
        defaultUserDetails.id = user.getId();
        defaultUserDetails.email = user.getEmail();
        defaultUserDetails.password = user.getPassword();
        defaultUserDetails.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole().getName()));
        return defaultUserDetails;
    }

    public long getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
