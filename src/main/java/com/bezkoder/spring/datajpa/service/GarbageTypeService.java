package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.repository.GarbageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GarbageTypeService {

    @Autowired
    private GarbageTypeRepository garbageTypeRepository;

    public List<Garbage_type> findAll() {

        return garbageTypeRepository.findAll();
    }

    public Long count() {

        return garbageTypeRepository.count();
    }

    public void deleteById(Long userId) {

        garbageTypeRepository.deleteById(userId);
    }

    public Garbage_type  findByGarbageTypeId(long garbageTypeId){
        return garbageTypeRepository.findById(garbageTypeId).get();
    }
}