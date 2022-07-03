package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.repository.GarbageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarbageTypeService {

    @Autowired
    private GarbageTypeRepository garbageTypeRepository;
    public ResponseEntity<Garbage_type> getGarbage_typeById(long id) {
        Optional<Garbage_type> garbage_typeData = garbageTypeRepository.findById(id);

        if ( garbage_typeData.isPresent()) {
            return new ResponseEntity<>( garbage_typeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Garbage_type> createGarbage_type(Garbage_type garbage_type) {
        try {
            Garbage_type _garbage_type = garbageTypeRepository
                    .save(new Garbage_type(garbage_type.getType_name(), garbage_type.getUnit_price()));
            return new ResponseEntity<>(_garbage_type, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Garbage_type> patchGarbage_type(long id,Garbage_type garbage_type) {
        Optional<Garbage_type> garbage_typeData = garbageTypeRepository.findById(id);
        if (garbage_typeData.isPresent()) {
            Garbage_type _garbage_type = garbage_typeData.get();
            if(garbage_type.getType_name()!=null || garbage_type.getType_name()!= ""){
                _garbage_type.setType_name(garbage_type.getType_name());
            }
            if(garbage_type.getUnit_price()!=0 ){
                _garbage_type.setUnit_price(garbage_type.getUnit_price());
            }
            return new ResponseEntity<>(garbageTypeRepository.save(_garbage_type), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Garbage_type> updateGarbage_type(long id, Garbage_type garbage_type) {
        Optional<Garbage_type> garbage_typeData = garbageTypeRepository.findById(id);

        if (garbage_typeData.isPresent()) {
            System.out.println(garbage_type.getType_name()+garbage_type.getType_name());//Check Variable
            Garbage_type _garbage_type = garbage_typeData.get();
            _garbage_type.setType_name(garbage_type.getType_name());
            _garbage_type.setUnit_price(garbage_type.getUnit_price());
            return new ResponseEntity<>(garbageTypeRepository.save(_garbage_type), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Utils Layer
    public List<Garbage_type> findAll() {

        return garbageTypeRepository.findAll();
    }

    public void deleteById(Long userId) {

        garbageTypeRepository.deleteById(userId);
    }

    public Garbage_type  findByGarbageTypeId(long garbageTypeId){
        return garbageTypeRepository.findById(garbageTypeId).get();
    }
}