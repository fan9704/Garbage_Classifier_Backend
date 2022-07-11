package com.bezkoder.spring.datajpa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bank_acctDTO {
    private  long bank_type;
    private String account_code;
    private long user;
}