package com.bezkoder.spring.datajpa.model;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MachineDTO {

    private String location;
    private boolean user_lock;
    private boolean machine_lock;
    @Lob
    private Blob machinePicture;
    private Garbage_recordDTO garbage_records;
    private Machine_storageDTO machineStorages;

}
