package io.blog.springblogapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<AddressRequest> addresses;

}
