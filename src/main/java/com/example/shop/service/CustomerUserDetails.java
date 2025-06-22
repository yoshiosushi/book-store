package com.example.shop.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomerUserDetails implements UserDetails {

    private String email;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomerUserDetails(String email,String username, String password,
                               Collection<? extends GrantedAuthority> authorities
    ) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public void setEmail(String email) { this.email = email; }

    public String getDisplayUsername(){
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
