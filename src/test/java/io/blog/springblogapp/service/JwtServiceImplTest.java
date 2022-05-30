package io.blog.springblogapp.service;

import io.blog.springblogapp.SpringApplicationContext;
import io.blog.springblogapp.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class JwtServiceImplTest {

    private static final String ID = "0d7403bc-0ba6-47c9-9499-11aebfff6969";
    private static final String PUBLIC_USER_ID = "AilzjCjfKFXq9PQSHMFxa6EXG6cC6cdKLfC6d7Cb";
    private static final String FIRST_NAME = "Éric";
    private static final String LAST_NAME = "Magalhães";
    private static final String EMAIL = "test@test.com";
    private static final String ENCRYPTED_PASSWORD = "$2a$10$RToYv0ZYYBVVjdL6oftLheXszKlAef/WzhsD2It3eWY44XkASpXVu"; //123
    private static final String JWT_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZUBlbWFpbC5jb20iLCJleHAiOjE2NTMwMDY3NTh9.JBPbOXQpNHRDqcax2ItLeBuxK8akpzElwdpu62UZfPNEVighXOLamHuZeGpPlLv3rtrCgoieN9tsVSDfWOGC6g";

    @SpyBean
    JwtServiceImpl jwtService;

    UserDto user;

    @BeforeEach
    void setUp() {
        user = UserDto.builder().id(UUID.fromString(ID)).userId(PUBLIC_USER_ID).firstName(FIRST_NAME).lastName(LAST_NAME)
                .email(EMAIL).encryptedPassword(ENCRYPTED_PASSWORD).build();
    }

    @Test
    void test_is_valid_token() {
        //given

        //act
        boolean isValid = jwtService.isValidToken(JWT_TOKEN);

        //assert
        assertThat(isValid).isTrue();
    }
}
