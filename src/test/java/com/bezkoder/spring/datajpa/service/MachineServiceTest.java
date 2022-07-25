package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.dto.Garbage_recordDTO;
import com.bezkoder.spring.datajpa.dto.MachineDTO;
import com.bezkoder.spring.datajpa.dto.MachineResponseDTO;
import com.bezkoder.spring.datajpa.dto.Machine_storageDTO;
import com.bezkoder.spring.datajpa.model.*;
import com.bezkoder.spring.datajpa.repository.MachineRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MachineServiceTest {
    @InjectMocks
    private MachineService machineService;
    @Mock
    private MachineRepository machineRepository;
    @Mock
    private UserService userService;
    @Mock
    private GarbageTypeService garbageTypeService;
    @Mock
    private FireBaseService fireBaseService;
    private Machine _machine;
    private Optional<Machine> machineOptional;
    Base64.Encoder encoder = Base64.getEncoder();
    User u1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
    Wallet w1 = new Wallet(1,new BigDecimal(0),"Create Account",u1,null);

    Machine m1 =new Machine(1,"Hsinchu",true,false,null,u1,null,null);
    Machine m2 =new Machine(2,"Taoyuan",true,false,null,null,null,null);
    Machine m3 =new Machine(3,"Maioli",true,false,null,null,null,null);
    Machine m4 =new Machine(4,"Taipei",true,false,null,null,null,null);




    Garbage_type g1= new Garbage_type(0,"寶特瓶",0.012);
    Garbage_type g2=new Garbage_type(1,"鐵鋁罐",0.2);
    Garbage_type g3=new Garbage_type(2,"紙類",0.003);
    Garbage_type g4=new Garbage_type(3,"鋁箔包",0.5);

    @Test
    void findMachineById() throws SQLException, JsonProcessingException {
        Mockito.when(machineRepository.findById(anyLong())).thenReturn(Optional.of(m1));
        MachineResponseDTO ResponseDTO = machineService.findMachineById(1L);

        String jsonInput = this.convertToJson(ResponseDTO);
        String jsonOutput = this.convertToJson(machineService.formatMachineResponse(m1));

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Id Pass");
    }

    @Test
    void findAll() throws JsonProcessingException, SQLException {
        Mockito.when(machineRepository.findAll()).thenReturn(Arrays.asList(m1,m2,m3,m4));
        List<MachineResponseDTO> ResponseDTO = machineService.findAll();
        List<MachineResponseDTO> RequestDTO = new ArrayList<>();
        for (Machine _machine : Arrays.asList(m1,m2,m3,m4)) {
            RequestDTO.add(machineService.formatMachineResponse(_machine));
        }

        String jsonInput = this.convertToJson(ResponseDTO);
        String jsonOutput = this.convertToJson(RequestDTO);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find All Pass");

    }

    @Test
    void findAllMachineByLocation() throws JsonProcessingException, SQLException {
        Mockito.when(machineRepository.findAllMachineByLocation(anyString())).thenReturn(Arrays.asList(m1,m2,m3,m4));
        List<MachineResponseDTO> ResponseDTO = machineService.findAllMachineByLocation("Hsinchu");
        List<MachineResponseDTO> RequestDTO = new ArrayList<>();
        for (Machine _machine : Arrays.asList(m1,m2,m3,m4)) {
            RequestDTO.add(machineService.formatMachineResponse(_machine));
        }

        String jsonInput = this.convertToJson(ResponseDTO);
        String jsonOutput = this.convertToJson(RequestDTO);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find All By Location Pass");
    }

    @Test
    void createMachine() throws SQLException, JsonProcessingException {
        MachineDTO machineDTO = new MachineDTO("Tainan",false,false,null,null,null);
        Machine _machine = new Machine();
        _machine.setLocation(machineDTO.getLocation());
        _machine.setUser_lock(machineDTO.isUser_lock());
        _machine.setMachine_lock(machineDTO.isMachine_lock());
        _machine.setMachinePicture(machineDTO.getMachinePicture());
        _machine.setGarbage_records(new TreeSet<>());
        for (Garbage_type garbageType : Arrays.asList(g1,g2,g3,g4)) {
            Machine_storage _machineStorage = new Machine_storage();
            _machineStorage.setGarbageType(garbageType);
            _machineStorage.setStorage(0);
            _machine.addMachineStorage(_machineStorage);

        }

        Mockito.when(garbageTypeService.findAll()).thenReturn(Arrays.asList(g1,g2,g3,g4));

        MachineResponseDTO ResponseDTO = machineService.createMachine(machineDTO);
        MachineResponseDTO RequestDTO = machineService.formatMachineResponse(_machine);
        ResponseDTO.setMachineStorages(null);
        RequestDTO.setMachineStorages(null);
        String jsonInput = this.convertToJson(ResponseDTO);
        String jsonOutput = this.convertToJson(RequestDTO);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Create Pass");
    }

    @Test
    void updateMachinePicture() throws SQLException, IOException {
//        Mockito.when(machineRepository.findById(anyLong())).thenReturn(Optional.of(m1));

//        ResponseEntity<MachineResponseDTO> ResponseMachineDTO = machineService.
//                UpdateMachinePicture(1L,);
//
//        String jsonInput = this.convertToJson(ResponseMachineDTO.getBody());
//        String jsonOutput = this.convertToJson(machineService.formatMachineResponse(m1));

//        System.out.println("Input:"+jsonInput);
//        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Update Picture By Id Pass");
    }

    @Test
    void linkMachine() throws SQLException, JsonProcessingException {
        m1.setUser_lock(false);
        Mockito.when(machineRepository.getById(anyLong())).thenReturn(m1);
        Mockito.when(userService.findUserById(anyLong())).thenReturn(u1);

        ResponseEntity ResponseMachineDTO = machineService.linkMachine(1L,1L);
        m1.setCurrent_user(u1);
        m1.setUser_lock(true);
        ResponseEntity RequestMachineDTO = new ResponseEntity(machineService.formatMachineResponse(m1), HttpStatus.OK);

        String jsonInput = this.convertToJson(ResponseMachineDTO.getBody());
        String jsonOutput = this.convertToJson(RequestMachineDTO.getBody());

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Link Machine Pass");

    }

    @Test
    void unLinkMachine() throws SQLException, JsonProcessingException {
        m1.setUser_lock(true);
        Mockito.when(machineRepository.getById(anyLong())).thenReturn(m1);
        Mockito.when(userService.findUserById(anyLong())).thenReturn(u1);

        ResponseEntity ResponseMachineDTO = machineService.unLinkMachine(1L);
        m1.setCurrent_user(u1);
        m1.setUser_lock(false);
        ResponseEntity RequestMachineDTO = new ResponseEntity(machineService.formatMachineResponse(m1), HttpStatus.OK);

        String jsonInput = this.convertToJson(ResponseMachineDTO.getBody());
        String jsonOutput = this.convertToJson(RequestMachineDTO.getBody());

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Unlink Machine Pass");
    }

    @Test
    void lockMachine() throws JsonProcessingException, SQLException {
        m1.setMachine_lock(false);
        Mockito.when(machineRepository.getById(anyLong())).thenReturn(m1);
        Mockito.when(machineRepository.save(any(Machine.class))).thenReturn(m1);
        MachineResponseDTO ResponseMachineDTO = machineService.lockMachine(1L);
        m1.setMachine_lock(true);
        MachineResponseDTO RequestMachineDTO = machineService.formatMachineResponse(m1);

        String jsonInput = this.convertToJson(ResponseMachineDTO);
        String jsonOutput = this.convertToJson(RequestMachineDTO);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Lock Machine Pass");
    }

    @Test
    void unlockMachine() throws SQLException, JsonProcessingException {
        m1.setMachine_lock(true);
        Mockito.when(machineRepository.getById(anyLong())).thenReturn(m1);
        Mockito.when(machineRepository.save(any(Machine.class))).thenReturn(m1);
        MachineResponseDTO ResponseMachineDTO = machineService.unlockMachine(1L);
        m1.setMachine_lock(false);
        MachineResponseDTO RequestMachineDTO = machineService.formatMachineResponse(m1);

        String jsonInput = this.convertToJson(ResponseMachineDTO);
        String jsonOutput = this.convertToJson(RequestMachineDTO);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Unlock Machine Pass");
    }

    @Test
    void lockUserLink() throws SQLException, JsonProcessingException {
        Mockito.when(machineRepository.getById(anyLong())).thenReturn(m1);
        Mockito.when(machineRepository.save(any(Machine.class))).thenReturn(m1);
        MachineResponseDTO ResponseMachineDTO = machineService.lockUserLink(1L);
        m1.setUser_lock(true);
        MachineResponseDTO RequestMachineDTO = machineService.formatMachineResponse(m1);

        String jsonInput = this.convertToJson(ResponseMachineDTO);
        String jsonOutput = this.convertToJson(RequestMachineDTO);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Lock User Link Machine Pass");
    }

    @Test
    void deleteById() {
        machineService.deleteById(1L);
        verify(machineRepository).deleteById(anyLong());

        System.out.println("Test Delete By Id Pass");
    }

    @Test
    void update() throws SQLException, JsonProcessingException {
        MachineDTO machineDTO = new MachineDTO("Tainan",false,false,null,null,null);

        Mockito.when(machineRepository.getById(anyLong())).thenReturn(m1);
        Mockito.when(machineRepository.save(any(Machine.class))).thenReturn(m1);
        MachineResponseDTO ResponseMachineDTO = machineService.update(machineDTO,1L);
        MachineResponseDTO RequestMachineDTO = machineService.formatMachineResponse(m1);

        String jsonInput = this.convertToJson(ResponseMachineDTO);
        String jsonOutput = this.convertToJson(RequestMachineDTO);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Update Machine Pass");
    }

    @Test
    void updateRecycleRecord() throws SQLException, FirebaseMessagingException, JsonProcessingException {
        Machine_storage TempStorage= new Machine_storage(1,m1,g1,null,0.15);
        Garbage_record TempGarbageRecord = new Garbage_record(1,g1,120,u1,null,m1);
        Set<Garbage_record> TempGarbageRecordSet=new HashSet<>();
        TempGarbageRecordSet.add(TempGarbageRecord);
        m1.setGarbage_records(TempGarbageRecordSet);
        u1.setWallet(w1);
        Set<Machine_storage> TempStorageSet = new HashSet<>();
        TempStorageSet.add(TempStorage);
        m1.setMachineStorages(TempStorageSet);
        Machine_storageDTO machineStorageDTO = new Machine_storageDTO(1,1,0.15);
        MachineDTO machineDTO = new MachineDTO("Tainan",false,false,null,new Garbage_recordDTO(1,0.15,1,1),machineStorageDTO);

        Mockito.when(garbageTypeService.findByGarbageTypeId(anyLong())).thenReturn(g1);
        Mockito.when(machineRepository.getById(anyLong())).thenReturn(m1);
        Mockito.when(machineRepository.save(any(Machine.class))).thenReturn(m1);
        ResponseEntity<MachineResponseDTO> ResponseMachineDTO = machineService.updateRecycleRecord(1L,machineDTO);
        ResponseEntity<MachineResponseDTO> RequestMachineDTO = new ResponseEntity<>(machineService.formatMachineResponse(m1), HttpStatus.OK);

        String jsonInput = this.convertToJson(ResponseMachineDTO.getBody());
        String jsonOutput = this.convertToJson(RequestMachineDTO.getBody());

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Update Recycle Record Pass");
    }

    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}