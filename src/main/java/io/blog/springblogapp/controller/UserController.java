package io.blog.springblogapp.controller;

import io.blog.springblogapp.dto.AddressDto;
import io.blog.springblogapp.dto.UserDto;
import io.blog.springblogapp.model.request.UserDetailsRequest;
import io.blog.springblogapp.model.request.UserUpdateRequest;
import io.blog.springblogapp.model.response.AddressResponse;
import io.blog.springblogapp.model.response.UserResponse;
import io.blog.springblogapp.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping(value = "/{id}/addresses", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<AddressResponse>> getUserAddresses(@PathVariable("id") String userId) {
        List<AddressDto> foundAddresses = userService.getUserAddresses(userId);

        Type listType = new TypeToken<List<AddressResponse>>() {}.getType();
        List<AddressResponse> response = modelMapper.map(foundAddresses, listType);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String userId) {
        UserResponse response = new UserResponse();

        UserDto foundUser = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(foundUser, response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "limit", defaultValue = "15") int limit) {

        List<UserResponse> response = userService.getAllUsers(page, limit);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                 consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDetailsRequest request) {
        UserDto userDto = modelMapper.map(request, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        UserResponse response = modelMapper.map(createdUser, UserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                                 consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") String userId,
                                                   @RequestBody UserUpdateRequest request) {

        UserDto userDTO = new UserDto();
        BeanUtils.copyProperties(request, userDTO);

        UserDto updatedUser = userService.updateUser(userId, userDTO);

        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(updatedUser, response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
