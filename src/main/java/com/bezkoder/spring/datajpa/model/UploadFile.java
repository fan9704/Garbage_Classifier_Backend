package com.bezkoder.spring.datajpa.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class UploadFile {
    private String extraField;
    private MultipartFile[] files;
}
