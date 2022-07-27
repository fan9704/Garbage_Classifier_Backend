package com.bezkoder.spring.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MachinePictureDTO {
    private Blob machinePicture;
}
