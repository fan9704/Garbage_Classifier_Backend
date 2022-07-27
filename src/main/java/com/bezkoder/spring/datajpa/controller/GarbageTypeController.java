package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GarbageTypeController {

    @Autowired
    private GarbageTypeService garbage_typeService;
    @GetMapping("/garbage_types")
    public List<Garbage_type> allGarbage_types() {
        return garbage_typeService.findAll();
    }

    @GetMapping("/garbage_type/{id}")
    public ResponseEntity<Garbage_type> getGarbage_typeById(@PathVariable("id") long id) {
        return garbage_typeService.getGarbage_typeById(id);
    }


    @PostMapping("/garbage_type")
    public ResponseEntity<Garbage_type> createGarbage_type(@RequestBody Garbage_type garbage_type) {
        return garbage_typeService.createGarbage_type(garbage_type);
    }

    @PatchMapping("/garbage_type/{id}")
    public ResponseEntity<Garbage_type> patchGarbage_type(@PathVariable("id") long id, @RequestBody Garbage_type garbage_type) {
        return garbage_typeService.patchGarbage_type(id,garbage_type);
    }

    @PutMapping("/garbage_type/{id}")
    public ResponseEntity<Garbage_type> updateGarbage_type(@PathVariable("id") long id, @RequestBody Garbage_type garbage_type) {
        return garbage_typeService.updateGarbage_type(id,garbage_type);
    }
    @DeleteMapping("/garbage_type/{id}")
    public void delete(@PathVariable String id) {
        garbage_typeService.deleteById(Long.parseLong(id));
    }
}