package io.blog.springblogapp.service.impl;

import io.blog.springblogapp.dto.AddressDto;
import io.blog.springblogapp.dto.UserDto;
import io.blog.springblogapp.exception.AddressNotFoundException;
import io.blog.springblogapp.exception.AuthException;
import io.blog.springblogapp.exception.BusinessException;
import io.blog.springblogapp.exception.UserNotFoundException;
import io.blog.springblogapp.model.entity.AddressEntity;
import io.blog.springblogapp.model.entity.UserEntity;
import io.blog.springblogapp.repository.AddressRepository;
import io.blog.springblogapp.repository.UserRepository;
import io.blog.springblogapp.service.UserService;
import io.blog.springblogapp.utils.ErrorMessages;
import io.blog.springblogapp.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Integer PUBLIC_ID_LENGTH = 40;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final Utils utils;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public UserDto createUser(UserDto userDTO) {
        verifyIfUserAlreadyExists(userDTO);

        for (AddressDto address : userDTO.getAddresses()) {
            address.setUser(userDTO);
            address.setAddressId(utils.generateAddressId(PUBLIC_ID_LENGTH));
        }

        UserEntity user = modelMapper.map(userDTO, UserEntity.class);

        String publicUserId = utils.generateUserId(PUBLIC_ID_LENGTH);
        user.setUserId(publicUserId);
        user.setEncryptedPassword(passwordEncoder.encode(userDTO.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUser(String email) {
        UserEntity user = findUserByEmail(email);

        return modelMapper.map(user, UserDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> userEntities = userRepository.findAll(pageableRequest);
        List<UserEntity> users = userEntities.toList();

        Type listType = new TypeToken<List<UserDto>>() {}.getType();

        return modelMapper.map(users, listType);
    }

    @Override
    public List<AddressDto> getUserAddresses(String userId) {
        UserEntity foundUser = findUserByUserId(userId);

        if (!foundUser.getAddresses().isEmpty()) {
            Type listType = new TypeToken<List<AddressDto>>() {}.getType();
            return modelMapper.map(foundUser.getAddresses(), listType);
        }

        return new ArrayList<>();
    }

    @Override
    public AddressDto getUserAddress(String userId, String addressId) {
        UserEntity foundUser = findUserByUserId(userId);
        AddressEntity foundAddress = findByAddressId(addressId);

        if (!foundUser.equals(foundAddress.getUser())) {
            throw new BusinessException(ErrorMessages.ADDRESS_NOT_BELONG_USER.getErrorMessage());
        }

        return modelMapper.map(foundAddress, AddressDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity foundUser = findUserByUserId(userId);

        return modelMapper.map(foundUser, UserDto.class);
    }

    @Transactional
    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserEntity foundUser = findUserByUserId(userId);

        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());

        UserEntity updatedUser = userRepository.save(foundUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {
        UserEntity foundUser = findUserByUserId(userId);

        userRepository.delete(foundUser);
    }

    @Transactional(readOnly = true)
    private void verifyIfUserAlreadyExists(UserDto userDTO) {
        Optional<UserEntity> result = userRepository.findByEmail(userDTO.getEmail());
        if (result.isPresent()) {
            throw new AuthException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }
    }

    @Transactional(readOnly = true)
    private UserEntity findUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.NO_RECORD_FOUND_USERNAME.getErrorMessage());
        }
        return user.get();
    }

    @Transactional(readOnly = true)
    private UserEntity findUserByUserId(String userId) {
        Optional<UserEntity> foundUser = userRepository.findByUserId(userId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.NO_RECORD_FOUND_ID.getErrorMessage());
        }

        return foundUser.get();
    }

    @Transactional(readOnly = true)
    private AddressEntity findByAddressId(String addressId) {
        Optional<AddressEntity> foundAddress = addressRepository.findByAddressId(addressId);
        if (foundAddress.isEmpty()) {
            throw new AddressNotFoundException(ErrorMessages.NO_RECORD_FOUND_ID.getErrorMessage());
        }

        return foundAddress.get();
    }
}
