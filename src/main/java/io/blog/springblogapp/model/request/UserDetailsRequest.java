package io.blog.springblogapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
