package io.blog.springblogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String encryptedPassword;

    private String emailVerificationToken;

    private Boolean emailVerificationStatus = false;

}
