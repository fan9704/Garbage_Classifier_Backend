package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.dto.Garbage_recordDTO;
import com.bezkoder.spring.datajpa.model.Garbage_record;
import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.model.Machine;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.GarbageRecordRepository;
import com.bezkoder.spring.datajpa.repository.GarbageTypeRepository;
import com.bezkoder.spring.datajpa.repository.MachineRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GarbageRecordService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private GarbageTypeRepository garbageTypeRepository;
    @Autowired
    private GarbageRecordRepository garbageRecordRepository;
    public ResponseEntity<Garbage_record> getGarbage_recordById(long id) {
        Optional<Garbage_record> garbage_recordData = garbageRecordRepository.findById(id);
        if ( garbage_recordData.isPresent()) {
            return new ResponseEntity<>( garbage_recordData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Garbage_record> createGarbage_record(Garbage_recordDTO garbage_recordDTO) {
        try {
            Optional<User> userData =userRepository.findById(garbage_recordDTO.getUser());
            Optional<Machine> machineData =machineRepository.findById(garbage_recordDTO.getMachine_id());
            Optional<Garbage_type> garbage_typeData =garbageTypeRepository.findById(garbage_recordDTO.getGarbage_type());
            Garbage_record _garbage_record = garbageRecordRepository
                    .save(new Garbage_record(garbage_typeData.get(), garbage_recordDTO.getWeight(),userData.get(),machineData.get()));
            return new ResponseEntity<>(_garbage_record, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Garbage_record> patchGarbage_record(long id,Garbage_record garbage_record) {
        Optional<Garbage_record> garbage_recordData = garbageRecordRepository.findById(id);

        if (garbage_recordData.isPresent()) {
            Garbage_record _garbage_record = garbage_recordData.get();
            if(garbage_record.getGarbage_type()!=null){
                _garbage_record.setGarbage_type(garbage_record.getGarbage_type());
            }
            if(garbage_record.getWeight()!=0 ){
                _garbage_record.setWeight(garbage_record.getWeight());
            }
            return new ResponseEntity<>(garbageRecordRepository.save(_garbage_record), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Garbage_record> updateGarbage_record(long id,Garbage_record garbage_record) {
        Optional<Garbage_record> garbage_recordData = garbageRecordRepository.findById(id);

        if (garbage_recordData.isPresent()) {
            Garbage_record _garbage_record = garbage_recordData.get();
            _garbage_record.setGarbage_type(garbage_record.getGarbage_type());
            _garbage_record.setWeight(garbage_record.getWeight());
            _garbage_record.setUser(garbage_record.getUser());
            _garbage_record.setTime(garbage_record.getTime());
            _garbage_record.setMachine_id(garbage_record.getMachine_id());
            return new ResponseEntity<>(garbageRecordRepository.save(_garbage_record), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Utils Layer
    public List<Garbage_record> findAll() {
        List<Garbage_record> garbage_types = new ArrayList<Garbage_record>();
        garbageRecordRepository.findAll().forEach(e -> garbage_types.add(e));
        return garbage_types;
    }


    public void deleteById(Long userId) {

        garbageRecordRepository.deleteById(userId);
    }

}