package io.blog.springblogapp.controller;

import io.blog.springblogapp.dto.UserDto;
import io.blog.springblogapp.model.UserEntity;
import io.blog.springblogapp.model.request.UserDetailsRequest;
import io.blog.springblogapp.model.request.UserUpdateRequest;
import io.blog.springblogapp.model.response.UserDetailsResponse;
import io.blog.springblogapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("id") String userId) {
        UserDetailsResponse response = new UserDetailsResponse();

        UserDto foundUser = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(foundUser, response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<UserDetailsResponse>> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", defaultValue = "15") int limit) {

        List<UserDetailsResponse> response = userService.getAllUsers(page, limit);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                 consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserDetailsResponse> createUser(@RequestBody UserDetailsRequest request) {
        UserDetailsResponse response = new UserDetailsResponse();
        UserDto userDTO = new UserDto();

        BeanUtils.copyProperties(request, userDTO);

        UserDto createdUser = userService.createUser(userDTO);
        BeanUtils.copyProperties(createdUser, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                                 consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserDetailsResponse> updateUser(@PathVariable("id") String userId,
                                                          @RequestBody UserUpdateRequest request) {

        UserDto userDTO = new UserDto();
        BeanUtils.copyProperties(request, userDTO);

        UserDto updatedUser = userService.updateUser(userId, userDTO);

        UserDetailsResponse response = new UserDetailsResponse();
        BeanUtils.copyProperties(updatedUser, response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
