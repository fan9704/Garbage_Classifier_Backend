package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.dto.Machine_storageDTO;
import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.model.Machine;
import com.bezkoder.spring.datajpa.model.Machine_storage;
import com.bezkoder.spring.datajpa.repository.GarbageTypeRepository;
import com.bezkoder.spring.datajpa.repository.MachineRepository;
import com.bezkoder.spring.datajpa.repository.MachineStorageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MachineStorageServiceTest {
    @InjectMocks
    private MachineStorageService machineStorageService;
    @Mock
    private MachineStorageRepository machineStorageRepository;
    @Mock
    private MachineRepository machineRepository;
    @Mock
    private GarbageTypeRepository garbageTypeRepository;
    Machine machine1 =new Machine(1,"Hsinchu",true,false,null,null,null,null);

    Garbage_type g1= new Garbage_type(0,"寶特瓶",0.012);
    Garbage_type g2=new Garbage_type(1,"鐵鋁罐",0.2);
    Garbage_type g3=new Garbage_type(2,"紙類",0.003);
    Garbage_type g4=new Garbage_type(3,"鋁箔包",0.5);
    Machine_storage m1 = new Machine_storage(1,machine1,g1,null,0.15);
    Machine_storage m2 = new Machine_storage(2,machine1,g2,null,0.25);
    Machine_storage m3 = new Machine_storage(3,machine1,g3,null,0.35);
    Machine_storage m4 = new Machine_storage(4,machine1,g4,null,0.45);
    @Test
    void getMachine_storageById() throws JsonProcessingException {
        Mockito.when(machineStorageRepository.findById(anyLong())).thenReturn(Optional.of(m1));
        ResponseEntity<Machine_storage> ResMachineStorageRecord = machineStorageService.getMachine_storageById(1L);

        String jsonInput = this.convertToJson(ResMachineStorageRecord.getBody());
        String jsonOutput = this.convertToJson(m1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Id Pass");
    }

    @Test
    void getMachine_storageByMachineId() throws JsonProcessingException {
        List<Machine_storage> ListMachineStorage = new ArrayList<>(Arrays.asList(m1,m2,m3,m4));

        Mockito.when(machineRepository.findById(anyLong())).thenReturn(Optional.of(machine1));
        Mockito.when(machineStorageRepository.findByMachine(any(Machine.class))).thenReturn(ListMachineStorage);

        List<Machine_storage> ResMachineStorageRecord = machineStorageService.getMachine_storageByMachineId(1L);

        String jsonInput = this.convertToJson(ResMachineStorageRecord);
        String jsonOutput = this.convertToJson(ListMachineStorage);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By MachineId Pass");
    }

    @Test
    void getMachine_storageByGarbageType() throws JsonProcessingException {
        List<Machine_storage> ListMachineStorage = new ArrayList<>(Arrays.asList(m1,m2,m3,m4));

        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(g1));
        Mockito.when(machineStorageRepository.findByGarbageType(any(Garbage_type.class))).thenReturn(ListMachineStorage);

        List<Machine_storage> ResMachineStorageRecord = machineStorageService.getMachine_storageByGarbageType(1L);

        String jsonInput = this.convertToJson(ResMachineStorageRecord);
        String jsonOutput = this.convertToJson(ListMachineStorage);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By GarbageTypeId Pass");
    }

    @Test
    void createMachine_storage() throws JsonProcessingException {
        Machine_storageDTO machine_storageDTO= new Machine_storageDTO(1,1,0.11);

        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(g1));
        Mockito.when(machineRepository.findById(anyLong())).thenReturn(Optional.of(machine1));
        Mockito.when(machineStorageRepository.save(any(Machine_storage.class))).thenReturn(m1);

        ResponseEntity<Machine_storage> ResMachineStorageRecord = machineStorageService.createMachine_storage(machine_storageDTO);

        String jsonInput = this.convertToJson(ResMachineStorageRecord.getBody());
        String jsonOutput = this.convertToJson(m1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Create Pass");
    }

    @Test
    void patchMachine_storageWithJSON() throws JsonProcessingException {
        Machine_storageDTO machine_storageDTO= new Machine_storageDTO(1,1,0.11);

        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(g1));
        Mockito.when(machineRepository.findById(anyLong())).thenReturn(Optional.of(machine1));
        Mockito.when(machineStorageRepository.findOneByMachineAndGarbageType(any(Machine.class),any(Garbage_type.class))).thenReturn(m1);
        Mockito.when(machineStorageRepository.save(any(Machine_storage.class))).thenReturn(m1);

        ResponseEntity<Machine_storage> ResMachineStorageRecord = machineStorageService.patchMachine_storageWithJSON(machine_storageDTO);

        String jsonInput = this.convertToJson(ResMachineStorageRecord.getBody());
        String jsonOutput = this.convertToJson(m1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Patch By Json Pass");
    }

    @Test
    void patchMachine_storage() throws JsonProcessingException {
        Machine_storageDTO machine_storageDTO= new Machine_storageDTO(1,1,0.11);

        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(g1));
        Mockito.when(machineRepository.findById(anyLong())).thenReturn(Optional.of(machine1));
        Mockito.when(machineStorageRepository.findById(anyLong())).thenReturn(Optional.of(m1));
        Mockito.when(machineStorageRepository.save(any(Machine_storage.class))).thenReturn(m1);

        ResponseEntity<Machine_storage> ResMachineStorageRecord = machineStorageService.patchMachine_storage(1L,machine_storageDTO);

        String jsonInput = this.convertToJson(ResMachineStorageRecord.getBody());
        String jsonOutput = this.convertToJson(m1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Patch By Id Pass");
    }

    @Test
    void updateMachine_storage() throws JsonProcessingException {
        Machine_storageDTO machine_storageDTO= new Machine_storageDTO(1,1,0.11);

        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(g1));
        Mockito.when(machineRepository.findById(anyLong())).thenReturn(Optional.of(machine1));
        Mockito.when(machineStorageRepository.findById(anyLong())).thenReturn(Optional.of(m1));
        Mockito.when(machineStorageRepository.save(any(Machine_storage.class))).thenReturn(m1);

        ResponseEntity<Machine_storage> ResMachineStorageRecord = machineStorageService.updateMachine_storage(1L,machine_storageDTO);

        String jsonInput = this.convertToJson(ResMachineStorageRecord.getBody());
        String jsonOutput = this.convertToJson(m1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Put By Id Pass");
    }

    @Test
    void findAll() throws JsonProcessingException {
        List<Machine_storage> ListMachineStorage = new ArrayList<>(Arrays.asList(m1,m2,m3,m4));

        Mockito.when(machineStorageRepository.findAll()).thenReturn(ListMachineStorage);

        List<Machine_storage> ResMachineStorage = machineStorageService.findAll();

        String jsonInput = this.convertToJson(ResMachineStorage);
        String jsonOutput = this.convertToJson(ListMachineStorage);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find All Pass");
    }

    @Test
    void deleteById() {
        machineStorageService.deleteById(1L);
        verify(machineStorageRepository).deleteById(anyLong());

        System.out.println("Test Delete By Id Pass");
    }
    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}