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
public class AddressDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String addressId;

    private String city;

    private String country;

    private String streetName;

    private String postalCode;

    private String type;

    private UserDto user;
}
