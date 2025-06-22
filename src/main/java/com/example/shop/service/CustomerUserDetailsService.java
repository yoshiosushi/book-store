package com.example.shop.service;

import com.example.shop.model.entity.User;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(
                        user -> new CustomerUserDetails(
                                user.getEmail(),
                                user.getUsername(),
                                user.getPassword(),
                                toGrantedAuthorityList(user.getAuthority())
                        )
                ).orElseThrow(
                        () -> new UsernameNotFoundException(
                                "メールアドレス：" + email + " のアカウントは存在しません。")
                );
    }

    private List<GrantedAuthority> toGrantedAuthorityList(User.Authority authority){
        return Collections.singletonList(new SimpleGrantedAuthority(authority.name()));
    }

}