package io.blog.springblogapp.service.impl;

import io.blog.springblogapp.dto.UserDto;
import io.blog.springblogapp.exception.AuthException;
import io.blog.springblogapp.model.UserEntity;
import io.blog.springblogapp.repository.UserRepository;
import io.blog.springblogapp.service.UserService;
import io.blog.springblogapp.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Utils utils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDTO) {
        verifyIfUserAlreadyExists(userDTO);

        UserEntity user = new UserEntity();
        UserDto response = new UserDto();

        BeanUtils.copyProperties(userDTO, user);

        String publicUserId = utils.generateUserId(40);
        user.setUserId(publicUserId);
        user.setEncryptedPassword(passwordEncoder.encode(userDTO.getPassword()));

        UserEntity savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, response);

        return response;
    }

    private void verifyIfUserAlreadyExists(UserDto userDTO) {
        Optional<UserEntity> result = userRepository.findByEmail(userDTO.getEmail());
        if (result.isPresent()) {
            throw new AuthException("Already exists a account with this email.");
        }
    }
}
