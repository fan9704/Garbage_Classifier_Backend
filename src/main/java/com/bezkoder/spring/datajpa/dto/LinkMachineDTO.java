package com.bezkoder.spring.datajpa.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkMachineDTO {
    private  long id;
    private  long  current_user;
}
