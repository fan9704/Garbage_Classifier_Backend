package com.bezkoder.spring.datajpa.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
public class Transfer_money_recordDTO {
    private  long receiver;
    private BigDecimal amount;
    private String bank_name;

}
