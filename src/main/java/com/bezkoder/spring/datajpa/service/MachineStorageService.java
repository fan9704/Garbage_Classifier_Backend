package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.dto.Machine_storageDTO;
import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.model.Machine;
import com.bezkoder.spring.datajpa.model.Machine_storage;
import com.bezkoder.spring.datajpa.repository.GarbageTypeRepository;
import com.bezkoder.spring.datajpa.repository.MachineRepository;
import com.bezkoder.spring.datajpa.repository.MachineStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MachineStorageService {

    @Autowired
    private MachineStorageRepository machineStorageRepository;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private GarbageTypeRepository garbageTypeRepository;

    public ResponseEntity<Machine_storage> getMachine_storageById(long id) {
        Optional<Machine_storage> machine_storageData = machineStorageRepository.findById(id);
        if ( machine_storageData.isPresent()) {
            return new ResponseEntity<>( machine_storageData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public List<Machine_storage> getMachine_storageByMachineId(long id) {
        try{
            Optional<Machine> machineData=machineRepository.findById(id);
            List<Machine_storage> machine_storageData;
            if(machineData.isPresent()){
                machine_storageData = machineStorageRepository.findByMachine(machineData.get());
            }else{
                machine_storageData = new ArrayList<>();
            }
            return machine_storageData;
        }catch (Exception e){
            List<Machine_storage> machine_storageData = new ArrayList<>();
            return machine_storageData;
        }
    }
    public List<Machine_storage> getMachine_storageByGarbageType(long id) {
        try{
            Optional<Garbage_type> garbage_typeData=garbageTypeRepository.findById(id);
            List<Machine_storage> machine_storageData;
            if(garbage_typeData.isPresent()){
                machine_storageData = machineStorageRepository.findByGarbageType(garbage_typeData.get());
            }else{
                machine_storageData = new ArrayList<>();
            }
            return machine_storageData;
        }catch (Exception e){
            List<Machine_storage> machine_storageData = new ArrayList<>();
            return machine_storageData;
        }
    }
    public ResponseEntity<Machine_storage> createMachine_storage(Machine_storageDTO machine_storageDTO) {
        try {
            Machine machine=machineRepository.findById(machine_storageDTO.getMachine_id()).get();
            Garbage_type garbage_type=garbageTypeRepository.findById(machine_storageDTO.getGarbage_type()).get();
            Machine_storage _machine_storage = machineStorageRepository
                    .save(new Machine_storage(machine,garbage_type,machine_storageDTO.getStorage()));
            return new ResponseEntity<>(_machine_storage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Machine_storage> patchMachine_storageWithJSON(Machine_storageDTO machine_storageDTO) {
        try{
            Machine machine=machineRepository.findById(machine_storageDTO.getMachine_id()).get();
            Garbage_type garbage_type=garbageTypeRepository.findById(machine_storageDTO.getGarbage_type()).get();
            Machine_storage machine_storageData = machineStorageRepository.findOneByMachineAndGarbageType(machine,garbage_type);
            machine_storageData.setStorage(machine_storageDTO.getStorage());
            if(machine_storageData.getStorage()>=0.5){
                System.out.println("Sent 50% notification");
            }else if(machine_storageData.getStorage()>=0.8){
                System.out.println("Sent 80% notification");
            }
            return new ResponseEntity<>(machineStorageRepository.save(machine_storageData), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Machine_storage> patchMachine_storage(long id,Machine_storageDTO machine_storageDTO) {
        Optional<Machine_storage> machine_storageData = machineStorageRepository.findById(id);
        Machine machine=machineRepository.findById(machine_storageDTO.getMachine_id()).get();
        Garbage_type garbage_type=garbageTypeRepository.findById(machine_storageDTO.getGarbage_type()).get();
        if (machine_storageData.isPresent()) {
            Machine_storage _machine_storage = machine_storageData.get();
            if(machine_storageDTO.getMachine_id()!=machine_storageData.get().getMachine().getId() ){
                _machine_storage.setMachine(machine);
            }
            if(machine_storageDTO.getGarbage_type()!=machine_storageData.get().getGarbageType().getId()){
                _machine_storage.setGarbageType(garbage_type);
            }
            return new ResponseEntity<>(machineStorageRepository.save(_machine_storage), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Machine_storage> updateMachine_storage(long id,Machine_storageDTO machine_storageDTO) {
        Optional<Machine_storage> machine_storageData = machineStorageRepository.findById(id);
        Machine machine=machineRepository.findById(machine_storageDTO.getMachine_id()).get();
        Garbage_type garbage_type=garbageTypeRepository.findById(machine_storageDTO.getGarbage_type()).get();
        if (machine_storageData.isPresent()) {
            Machine_storage _machine_storage = machine_storageData.get();
            _machine_storage.setMachine(machine);
            _machine_storage.setGarbageType(garbage_type);
            return new ResponseEntity<>(machineStorageRepository.save(_machine_storage), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Utils Layer
    public List<Machine_storage> findAll() {
        List<Machine_storage> machine_storages = new ArrayList<>();
        machineStorageRepository.findAll().forEach(e -> machine_storages.add(e));

        return machine_storages;
    }


    public void deleteById(Long userId) {

        machineStorageRepository.deleteById(userId);
    }
}