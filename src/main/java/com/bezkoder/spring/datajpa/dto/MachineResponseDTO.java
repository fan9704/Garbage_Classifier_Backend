package com.bezkoder.spring.datajpa.dto;

import com.bezkoder.spring.datajpa.model.Garbage_record;
import com.bezkoder.spring.datajpa.model.Machine_storage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MachineResponseDTO {
    private long id;
    private String location;
    private boolean user_lock;
    private boolean machine_lock;
    private Set<Garbage_record> garbage_records;
    private Set<Machine_storage> machineStorages;
    private String machinePicture;
}
