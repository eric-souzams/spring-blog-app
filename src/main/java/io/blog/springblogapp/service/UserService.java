package io.blog.springblogapp.service;

import io.blog.springblogapp.dto.UserDto;
import io.blog.springblogapp.model.UserEntity;
import io.blog.springblogapp.model.response.UserDetailsResponse;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDTO);
    UserDto getUser(String email);
    UserDto getUserByUserId(String userId);
    UserDto updateUser(String userId, UserDto userDTO);
    void deleteUser(String userId);
    List<UserDetailsResponse> getAllUsers(int page, int limit);
}
