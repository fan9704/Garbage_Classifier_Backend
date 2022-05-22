package com.bezkoder.spring.datajpa.service;


import com.bezkoder.spring.datajpa.model.MachineDTO;
import com.bezkoder.spring.datajpa.model.*;
import com.bezkoder.spring.datajpa.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class  MachineService {

    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private GarbageTypeService garbageTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private MachineStorageService machineStorageService;

    private Machine _machine;
    private Optional<Machine> machineOptional;

    public Machine findMachineById(long mahcineId){
        machineOptional = machineRepository.findById(mahcineId);


        return machineOptional.get();
    }
    public List<Machine> findAll() {
        return machineRepository.findAll();
    }
    public Machine createMachine(MachineDTO machineDTO){
        _machine = new Machine();
        _machine.setMachinePicture(machineDTO.getMachinePicture());
        _machine.setLocation(machineDTO.getLocation());


        List<Garbage_type> garbageTypes = garbageTypeService.findAll();
        Machine_storage _machineStorage;

        for (Garbage_type garbageType : garbageTypes) {

            _machineStorage = new Machine_storage();
            _machineStorage.setGarbageType(garbageType);
            _machineStorage.setStorage(0);

            _machine.addMachineStorage(_machineStorage);

        }

           machineRepository.save(_machine);


        return  _machine;
    }
    public ResponseEntity linkMachine(long MachineId , long userId){

            _machine = findMachineById(MachineId);
            User _user = userService.findUserById(userId);

            if (!_machine.isUser_lock()){

                _machine.setCurrent_user(_user);
                _machine.setUser_lock(true);
                machineRepository.save(_machine);


                return new ResponseEntity(_machine,HttpStatus.OK);
            }

            else {
                  return new ResponseEntity(HttpStatus.CONFLICT);
            }
    }
    public ResponseEntity unLinkMachine(long MachineId){

        _machine = findMachineById(MachineId);
        User _user = userService.findUserById(0); // userId is anonymous user

        if (_machine.isUser_lock()){

            _machine.setCurrent_user(_user);
            _machine.setUser_lock(false);

            machineRepository.save(_machine);


            return new ResponseEntity(_machine,HttpStatus.OK);
        }

        else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
    public Machine lockMachine(long machineId){
        _machine = findMachineById(machineId);
        _machine.setMachine_lock(true);

        return  machineRepository.save(_machine);
    }
    public Machine unlockMachine(long machineId){
        _machine = findMachineById(machineId);
        _machine.setMachine_lock(false);

        return  machineRepository.save(_machine);
    }
    public Machine lockUserLink(long machineId){
        _machine = findMachineById(machineId);
        _machine.setUser_lock(true);

        return  machineRepository.save(_machine);
    }
    public Long count() {
        return machineRepository.count();
    }
    public void deleteById(Long machineId) {
        machineRepository.deleteById(machineId);
    }
    public Machine updata(MachineDTO machineDTO, long id) {
        _machine = findMachineById(id);
        _machine.setMachinePicture(machineDTO.getMachinePicture());
        _machine.setMachine_lock(machineDTO.isMachine_lock());
        _machine.setUser_lock(machineDTO.isUser_lock());
        _machine.setLocation(machineDTO.getLocation());

        return machineRepository.save(_machine);
    }

//    public ResponseEntity<Machine> recordInfo(long machineId , Set<Machine_storage> machineStorages, Garbage_recordDTO garbageRecordDTO) {
//
//            _machine = findMachineById(machineId);
//            User user ;
//
//            _machine.setMachineStorages(machineStorages);
//
//            Set<Garbage_record> garbage_records = new HashSet<>();
//            Garbage_record garbage_record = new Garbage_record();
//
//            garbage_record.setGarbage_type(garbageTypeService.findByGarbageTypeId((garbageRecordDTO.getGarbage_type())));
//            garbage_record.setWeight(garbageRecordDTO.getWeight());
//            garbage_record.setUser(_machine.getCurrent_user());
//            garbage_record.setMachine_id(_machine);
//            garbage_records.add(garbage_record);
//
//            _machine.setGarbage_records(garbage_records);
//
//        return  new ResponseEntity<>(machineRepository.save(_machine),HttpStatus.OK);
//    }



}
