package com.bezkoder.spring.datajpa.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transfer_money_recordDTO {
    private  long receiver;
    private BigDecimal amount;
    private String bank_name;

}
