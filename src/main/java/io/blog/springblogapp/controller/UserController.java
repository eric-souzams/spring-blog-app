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
    public ResponseEntity<?> getUser() {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDetailsRequest request) {
        UserDetailsResponse response = new UserDetailsResponse();
        UserDto userDTO = new UserDto();

        BeanUtils.copyProperties(request, userDTO);

        UserDto createdUser = userService.createUser(userDTO);
        BeanUtils.copyProperties(createdUser, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateUser() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        return null;
    }

}
