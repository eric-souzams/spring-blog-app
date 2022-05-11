package io.blog.springblogapp.service;

import io.blog.springblogapp.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDTO);
    UserDto getUser(String email);

}
