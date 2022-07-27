package com.bezkoder.spring.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  WalletDTO  {
    private BigDecimal change_value;//"- transfer money {transfer account}" "+ recycle {recycle_type} add {total_amount}"
    private String description;
    private String username;
}
