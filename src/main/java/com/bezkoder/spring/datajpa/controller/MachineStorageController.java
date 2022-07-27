package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Machine_storage;
import com.bezkoder.spring.datajpa.dto.Machine_storageDTO;
import com.bezkoder.spring.datajpa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MachineStorageController {
    @Autowired
    private MachineStorageService machine_storageService;
    @GetMapping("/machines_storage")
    public List<Machine_storage> allMachine_storages() {
        return machine_storageService.findAll();
    }

    @GetMapping("/machine_storage/{id}")
    public ResponseEntity<Machine_storage> getMachine_storageById(@PathVariable("id") long id) {
        return machine_storageService.getMachine_storageById(id);
    }
    @GetMapping("/machine_storage/machine/{id}")
    public List<Machine_storage> getMachine_storageByMachineId(@PathVariable("id") long id) {
        return machine_storageService.getMachine_storageByMachineId(id);
    }
    @GetMapping("/machine_storage/garbage_type/{id}")
    public List<Machine_storage> getMachine_storageByGarbageType(@PathVariable("id") long id) {
        return machine_storageService.getMachine_storageByGarbageType(id);
    }
    @PostMapping("/machine_storage")
    public ResponseEntity<Machine_storage> createMachine_storage(@RequestBody Machine_storageDTO machine_storageDTO) {
        return machine_storageService.createMachine_storage(machine_storageDTO);
    }
    @PatchMapping("/machine_storage")
    public ResponseEntity<Machine_storage> patchMachine_storageWithJSON(@RequestBody Machine_storageDTO machine_storageDTO) {
        return machine_storageService.patchMachine_storageWithJSON(machine_storageDTO);
    }
    @PatchMapping("/machine_storage/{id}")
    public ResponseEntity<Machine_storage> patchMachine_storage(@PathVariable("id") long id, @RequestBody Machine_storageDTO machine_storageDTO) {
        return machine_storageService.patchMachine_storage(id,machine_storageDTO);
    }
    @PutMapping("/machine_storage/{id}")
    public ResponseEntity<Machine_storage> updateMachine_storage(@PathVariable("id") long id, @RequestBody Machine_storageDTO machine_storageDTO) {
        return machine_storageService.updateMachine_storage(id,machine_storageDTO);
    }
    @DeleteMapping("/machine_storage/{id}")
    public void deleteMachine(@PathVariable String id) {
        machine_storageService.deleteById(Long.parseLong(id));
    }
}