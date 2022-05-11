package io.blog.springblogapp.service.impl;

import io.blog.springblogapp.exception.UserNotFoundException;
import io.blog.springblogapp.model.UserEntity;
import io.blog.springblogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Wrong credentials.");
        }

        return new User(user.get().getEmail(), user.get().getEncryptedPassword(), new ArrayList<>());
    }
}
