package io.blog.springblogapp.controller;

import io.blog.springblogapp.dto.UserDto;
import io.blog.springblogapp.model.request.UserDetailsRequest;
import io.blog.springblogapp.model.response.UserDetailsResponse;
import io.blog.springblogapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("id") String userId) {
        UserDetailsResponse response = new UserDetailsResponse();

        UserDto foundUser = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(foundUser, response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<UserDetailsResponse> createUser(@RequestBody UserDetailsRequest request) {
        UserDetailsResponse response = new UserDetailsResponse();
        UserDto userDTO = new UserDto();

        BeanUtils.copyProperties(request, userDTO);

        UserDto createdUser = userService.createUser(userDTO);
        BeanUtils.copyProperties(createdUser, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<UserDetailsResponse> updateUser() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        return null;
    }

}
