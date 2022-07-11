package com.bezkoder.spring.datajpa.dto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserTokenDTO {
    long id;
    String firebaseToken;
}