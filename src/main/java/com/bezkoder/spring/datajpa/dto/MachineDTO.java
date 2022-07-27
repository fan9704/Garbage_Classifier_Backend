package com.bezkoder.spring.datajpa.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.sql.Blob;


@AllArgsConstructor
@Getter
@Setter
public class MachineDTO {

    private String location;
    private boolean user_lock;
    private boolean machine_lock;
    private Blob machinePicture;
    private Garbage_recordDTO garbage_records;
    private Machine_storageDTO machineStorages;

}
