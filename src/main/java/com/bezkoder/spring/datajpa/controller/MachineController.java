package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.dto.MachineDTO;
import com.bezkoder.spring.datajpa.dto.MachineResponseDTO;
import com.bezkoder.spring.datajpa.service.MachineService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MachineController {

    @Autowired
    private MachineService machineService;

    @GetMapping("/machines")
    public List<MachineResponseDTO> getAllMachines() throws SQLException {
        return machineService.findAll();
    }

    @GetMapping("/machines/location")
    public List<MachineResponseDTO> getAllMachinesByLocation(String location) throws SQLException {
        return machineService.findAllMachineByLocation(location);
    }

    @GetMapping("/machine/{id}")
    public ResponseEntity<MachineResponseDTO> getMachineById(@PathVariable("id") long id) throws SQLException {
        return new ResponseEntity<>(machineService.findMachineById(id),HttpStatus.OK);
    }

    @PostMapping("/machine")
    public ResponseEntity<MachineResponseDTO> createMachine(@RequestBody MachineDTO machine) {
        try {
            return new ResponseEntity<>(machineService.createMachine(machine), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/machine/{machineId}/link/{userId}")
    public ResponseEntity<MachineResponseDTO> linkMachine(@PathVariable("machineId") long machineId ,@PathVariable("userId") long userId) throws SQLException {
            return machineService.linkMachine(machineId,userId);
    }

    @PatchMapping("/machine/{machineId}/unlink")
    public ResponseEntity<MachineResponseDTO> unlinkMachine(@PathVariable("machineId") long machineId ) throws SQLException {
        return machineService.unLinkMachine(machineId);
    }
    @PatchMapping("/machine/{machineId}/lockUserLink")
    public ResponseEntity<MachineResponseDTO> lockUserLink(@PathVariable("machineId") long machineId) throws SQLException {
        return new ResponseEntity<>(machineService.lockUserLink(machineId),HttpStatus.OK);
    }
    @PatchMapping("/machine/{machineId}")
    public ResponseEntity<MachineResponseDTO> updateRecycleRecord(@PathVariable("machineId") long machineId, MachineDTO machine) throws SQLException, FirebaseMessagingException {
        return machineService.updateRecycleRecord(machineId, machine);
    }
    @PatchMapping("/machine/unlock/{machineId}")
    public ResponseEntity<MachineResponseDTO> unlockMachine(@PathVariable("machineId") long machineId){
        try{
            return new ResponseEntity<>(machineService.unlockMachine(machineId),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/machine/lock/{machineId}")
    public ResponseEntity<MachineResponseDTO> lockMachine(@PathVariable("machineId") long machineId){
        try{
            return new ResponseEntity<>(machineService.lockMachine(machineId),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/machine/{id}")
    public ResponseEntity<MachineResponseDTO> updateMachine(@PathVariable("id") long id,MachineDTO machineDTO) throws SQLException {
        return new ResponseEntity(machineService.update(machineDTO,id), HttpStatus.OK);
    }
    @DeleteMapping("/machine/{machineId}")
    public ResponseEntity<MachineResponseDTO> deleteMachine(@PathVariable("machineId") long machineId) {
        machineService.deleteById(machineId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }




}