package io.blog.springblogapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
        
    private String addressId;

    private String city;

    private String country;

    private String streetName;

    private String postalCode;

    private String type;
}
