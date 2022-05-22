package com.bezkoder.spring.datajpa.service;


import com.bezkoder.spring.datajpa.model.MachineDTO;
import com.bezkoder.spring.datajpa.model.*;
import com.bezkoder.spring.datajpa.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


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

    public Machine findMachineById(long mahcineId) {
        machineOptional = machineRepository.findById(mahcineId);


        return machineOptional.get();
    }

    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    public Machine createMachine(MachineDTO machineDTO) {
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


        return _machine;
    }

    public ResponseEntity linkMachine(long MachineId, long userId) {

        _machine = findMachineById(MachineId);
        User _user = userService.findUserById(userId);

        if (!_machine.isUser_lock()) {

            _machine.setCurrent_user(_user);
            _machine.setUser_lock(true);
            machineRepository.save(_machine);


            return new ResponseEntity(_machine, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity unLinkMachine(long MachineId) {

        _machine = findMachineById(MachineId);
        User _user = userService.findUserById(0); // userId is anonymous user

        if (_machine.isUser_lock()) {

            _machine.setCurrent_user(_user);
            _machine.setUser_lock(false);

            machineRepository.save(_machine);


            return new ResponseEntity(_machine, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    public Machine lockMachine(long machineId) {
        _machine = findMachineById(machineId);
        _machine.setMachine_lock(true);

        return machineRepository.save(_machine);
    }

    public Machine unlockMachine(long machineId) {
        _machine = findMachineById(machineId);
        _machine.setMachine_lock(false);

        return machineRepository.save(_machine);
    }

    public Machine lockUserLink(long machineId) {
        _machine = findMachineById(machineId);
        _machine.setUser_lock(true);

        return machineRepository.save(_machine);
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

    public ResponseEntity<Machine> updataRecycleRecord(long machineId, MachineDTO machine) {

        _machine = findMachineById(machineId);
        Set<Machine_storage> machineStorages = new HashSet<>();
        Garbage_record garbage_record =new Garbage_record();
        Set<Garbage_record> garbage_records = new HashSet<>();



        machineStorages.add(
                updataStorage(_machine.getMachineStorages(),machine.getMachineStorages())
        );
        _machine.setMachineStorages(machineStorages);



        garbage_record.setMachine_id(_machine);
        garbage_record.setGarbage_type(
                garbageTypeService.findByGarbageTypeId(
                        machine.getGarbage_records().getGarbage_type()
                )
        );
        garbage_record.setWeight(machine.getGarbage_records().getWeight());
        garbage_record.setUser(_machine.getCurrent_user());

        garbage_records.add(garbage_record);
        _machine.setGarbage_records(garbage_records);

        _machine.getCurrent_user().getWallet().setChange_value(
                _machine.getCurrent_user().getWallet().getChange_value().add(
                        new BigDecimal(garbage_record.getGarbage_type().getUnit_price() * garbage_record.getWeight())
                )
        );


        return new ResponseEntity<>(machineRepository.save(_machine), HttpStatus.OK);
    }

    public Machine_storage updataStorage(Set<Machine_storage> machineStorages, Machine_storageDTO machineStorage) {
        Iterator<Machine_storage> machineStorageIterator = machineStorages.iterator();
        Machine_storage machineStorageTmp;

        while (machineStorageIterator.hasNext()) {
            machineStorageTmp = machineStorageIterator.next();

            if (machineStorageTmp.getGarbageType().getId() == machineStorage.getGarbage_type()) {
                machineStorageTmp.setStorage(machineStorage.getStorage());
                return machineStorageTmp;
            }

        }
        return null;

    }


}

