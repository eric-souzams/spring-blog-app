package io.blog.springblogapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
        
    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private List<AddressResponse> addresses;

}
