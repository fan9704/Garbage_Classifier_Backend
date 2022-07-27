package com.bezkoder.spring.datajpa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Garbage_recordDTO {
    private  long garbage_type;
    private  double weight;
    private  long user;
    private  long machine_id;

}