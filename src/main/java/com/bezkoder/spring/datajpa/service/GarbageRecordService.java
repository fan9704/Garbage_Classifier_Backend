package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Garbage_record;
import com.bezkoder.spring.datajpa.repository.GarbageRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GarbageRecordService {

    @Autowired
    private GarbageRecordRepository garbageRecordRepository;

    public List<Garbage_record> findAll() {
        List<Garbage_record> garbage_types = new ArrayList<Garbage_record>();
        garbageRecordRepository.findAll().forEach(e -> garbage_types.add(e));

        return garbage_types;
    }

    public Long count() {

        return garbageRecordRepository.count();
    }

    public void deleteById(Long userId) {

        garbageRecordRepository.deleteById(userId);
    }

}