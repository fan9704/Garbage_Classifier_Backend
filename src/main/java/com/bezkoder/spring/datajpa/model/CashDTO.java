package com.bezkoder.spring.datajpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.*;


@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CashDTO {
    private long userId;
    private long cash;
}