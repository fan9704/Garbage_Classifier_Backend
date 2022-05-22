package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.model.Machine;
import com.bezkoder.spring.datajpa.model.Machine_storage;
import com.bezkoder.spring.datajpa.repository.MachineStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MachineStorageService {

    @Autowired
    private MachineStorageRepository machineStorageRepository;

    public List<Machine_storage> findAll() {
        List<Machine_storage> machine_storages = new ArrayList<Machine_storage>();
        machineStorageRepository.findAll().forEach(e -> machine_storages.add(e));

        return machine_storages;
    }

    public Long count() {

        return machineStorageRepository.count();
    }

    public Machine_storage updata(Machine_storage machineStorage){
        return machineStorageRepository.save(machineStorage);
    }

    public void deleteById(Long userId) {

        machineStorageRepository.deleteById(userId);
    }
    public List<Machine_storage> findByMachine(Machine machine){
        return machineStorageRepository.findByMachine(machine);
    }
    public List<Machine_storage> findByGarbageType(Garbage_type garbage_type){
        return  machineStorageRepository.findByGarbageType(garbage_type);
    }
    public  Machine_storage findOneByMachineAndGarbageType(Machine machine,Garbage_type garbage_type){
         return machineStorageRepository.findOneByMachineAndGarbageType(machine,garbage_type);

    }
}