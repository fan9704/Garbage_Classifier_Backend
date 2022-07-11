package com.bezkoder.spring.datajpa.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
    private String userName;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private Boolean active;

}
