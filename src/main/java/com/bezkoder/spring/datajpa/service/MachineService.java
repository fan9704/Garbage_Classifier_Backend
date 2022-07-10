package com.bezkoder.spring.datajpa.service;


import com.bezkoder.spring.datajpa.dto.MachineDTO;
import com.bezkoder.spring.datajpa.dto.MachinePictureDTO;
import com.bezkoder.spring.datajpa.dto.MachineResponseDTO;
import com.bezkoder.spring.datajpa.dto.Machine_storageDTO;
import com.bezkoder.spring.datajpa.model.*;
import com.bezkoder.spring.datajpa.repository.MachineRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;


@Service
public class MachineService {

    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private GarbageTypeService garbageTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private FireBaseService fireBaseService;


    private Machine _machine;
    private Optional<Machine> machineOptional;
    Base64.Encoder encoder = Base64.getEncoder();


    public MachineResponseDTO findMachineById(long machineId) throws SQLException {
        machineOptional = machineRepository.findById(machineId);
        if (machineOptional.isPresent()){
            return formatMachineResponse(machineOptional.get());
        }else{
            return formatMachineResponse(new Machine());
        }
    }

    public List<MachineResponseDTO> findAll() throws SQLException {
        List<Machine> _machineList = machineRepository.findAll();
        List<MachineResponseDTO> machineList = new ArrayList<>();
        for (Machine _machine : _machineList) {
            machineList.add(formatMachineResponse(_machine));
        }
        return machineList;
    }


    public List<MachineResponseDTO> findAllMachineByLocation(String location) throws SQLException {
        List<Machine> _machineList = machineRepository.findAllMachineByLocation(location);
        List<MachineResponseDTO> machineList = new ArrayList<>();
        for (Machine _machine : _machineList) {
            machineList.add(formatMachineResponse(_machine));
        }
        return machineList;
    }

    public MachineResponseDTO createMachine(MachineDTO machineDTO) throws SQLException {
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


        return formatMachineResponse(_machine);
    }
    public ResponseEntity<MachineResponseDTO> UpdateMachinePicture(long machineId, MultipartFile machinePictureDTO) throws SQLException, IOException {
        Machine _machine=machineRepository.findById(machineId).get();
        _machine.setMachinePicture(new SerialBlob(machinePictureDTO.getBytes()));
        machineRepository.save(_machine);
        return new ResponseEntity(formatMachineResponse(_machine),HttpStatus.OK);
    }
    public ResponseEntity linkMachine(long MachineId, long userId) throws SQLException {

        _machine = machineRepository.getById(MachineId);
        User _user = userService.findUserById(userId);

        if (!_machine.isUser_lock()) {

            _machine.setCurrent_user(_user);
            _machine.setUser_lock(true);
            machineRepository.save(_machine);


            return new ResponseEntity(formatMachineResponse(_machine), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity unLinkMachine(long MachineId) throws SQLException {

        _machine = machineRepository.getById(MachineId);
        User _user = userService.findUserById(0); // userId is anonymous user

        if (_machine.isUser_lock()) {

            _machine.setCurrent_user(_user);
            _machine.setUser_lock(false);

            machineRepository.save(_machine);


            return new ResponseEntity(formatMachineResponse(_machine), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    public MachineResponseDTO lockMachine(long machineId) throws SQLException {
        _machine = machineRepository.getById(machineId);
        _machine.setMachine_lock(true);
        
        return formatMachineResponse(machineRepository.save(_machine));
    }

    public MachineResponseDTO unlockMachine(long machineId) throws SQLException {
        _machine = machineRepository.getById(machineId);
        _machine.setMachine_lock(false);

        return formatMachineResponse(machineRepository.save(_machine));
    }

    public MachineResponseDTO lockUserLink(long machineId) throws SQLException {
        _machine = machineRepository.getById(machineId);
        _machine.setUser_lock(true);

        return formatMachineResponse(machineRepository.save(_machine));
    }

    public void deleteById(Long machineId) {
        machineRepository.deleteById(machineId);
    }

    public MachineResponseDTO update(MachineDTO machineDTO, long id) throws SQLException {
        _machine = machineRepository.getById(id);
        _machine.setMachinePicture(machineDTO.getMachinePicture());
        _machine.setMachine_lock(machineDTO.isMachine_lock());
        _machine.setUser_lock(machineDTO.isUser_lock());
        _machine.setLocation(machineDTO.getLocation());

        return formatMachineResponse(machineRepository.save(_machine));
    }

    public ResponseEntity<MachineResponseDTO> updateRecycleRecord(long machineId, MachineDTO machine) throws SQLException, FirebaseMessagingException {

        _machine = machineRepository.getById(machineId);
        Set<Machine_storage> machineStorages = new HashSet<>();
        Garbage_record garbage_record = new Garbage_record();
        Set<Garbage_record> garbage_records = new HashSet<>();


        machineStorages.add(
                updateStorage(_machine.getMachineStorages(), machine.getMachineStorages())
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




        return new ResponseEntity<>(formatMachineResponse(machineRepository.save(_machine)), HttpStatus.OK);
    }

    public Machine_storage updateStorage(Set<Machine_storage> machineStorages, Machine_storageDTO machineStorage) throws FirebaseMessagingException {
        Iterator<Machine_storage> machineStorageIterator = machineStorages.iterator();
        Machine_storage machineStorageTmp;

        while (machineStorageIterator.hasNext()) {
            machineStorageTmp = machineStorageIterator.next(); 
            if (machineStorageTmp.getGarbageType().getId() == machineStorage.getGarbage_type()) {
                if (machineStorageTmp.getStorage() <50 && machineStorage.getStorage()>=50){
                    sendNotification();
                }
                machineStorageTmp.setStorage(machineStorage.getStorage());
                return machineStorageTmp;
            }

        }
        return null;

    }

    private MachineResponseDTO formatMachineResponse(Machine _machine) throws SQLException {
        MachineResponseDTO machine = new MachineResponseDTO(
                _machine.getId(),
                _machine.getLocation(),
                _machine.isUser_lock(),
                _machine.isMachine_lock(),
                _machine.getGarbage_records(),
                _machine.getMachineStorages(),
                null);
        Blob blob = _machine.getMachinePicture();
        if (blob != null) {
            byte[] valueArr = null;
            valueArr = blob.getBytes(1L, (int) blob.length());
            String encodedText = encoder.encodeToString(valueArr);
            machine.setMachinePicture(encodedText);
        }
        return machine;
    }


    private void sendNotification() throws FirebaseMessagingException {
        Note note = new Note();
        note.setSubject("垃圾桶容量已達50%");
        note.setContent("請盡速派人清理");
        fireBaseService.sendNotification(note,"c4QJCd2FS-i15nJnC0gvFr:APA91bHPT0mToROLp_6seIwWg4e4YoVgVEB23RG9ml6WEYAWaShak4u1sF4UZoMBuMiQrIFN9dhJ0L1xy2G071dajhbHDyVTyC4CpJR6wbbQOPpDfTVzmwzhr8bfZYv5zE5AgFAt-AXS");


    }



}

