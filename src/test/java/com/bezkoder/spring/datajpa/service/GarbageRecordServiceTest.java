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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GarbageRecordServiceTest {
    private MockMvc mockMvc;
    @InjectMocks
    private GarbageRecordService garbageRecordService;
    @Mock
    private GarbageRecordRepository garbageRecordRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MachineRepository machineRepository;
    @Mock
    private GarbageTypeRepository garbageTypeRepository;
    Garbage_type gt1= new Garbage_type(0,"寶特瓶",0.012);
    Garbage_type gt2=new Garbage_type(1,"鐵鋁罐",0.2);
    User u1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
    User u2 =new User(2,"user2","user2@mail.com","user2","user2","user2",false,"001", new HashSet<>(),null);
    Machine m1 =new Machine(1,"Hsinchu",false,false,null,null,null,null);
    Machine m2 =new Machine(2,"Taoyun",false,false,null,null,null,null);
    Garbage_record g1= new Garbage_record(gt1,120,u1,m1);
    Garbage_record g2= new Garbage_record(gt2,360,u2,m2);
    Garbage_record g3= new Garbage_record(gt1,240,u1,m2);
    Garbage_record g4= new Garbage_record(gt2,480,u2,m1);

    @Test
    void getGarbage_recordById() throws JsonProcessingException {
        Mockito.when(garbageRecordRepository.findById(anyLong())).thenReturn(Optional.of(g1));
        ResponseEntity<Garbage_record> ResGarbageRecord = garbageRecordService.getGarbage_recordById(1L);

        String jsonInput = this.convertToJson(ResGarbageRecord.getBody());
        String jsonOutput = this.convertToJson(g1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Id Pass");
    }

    @Test
    void createGarbage_record() throws JsonProcessingException {
        Garbage_recordDTO garbage_recordDTO = new Garbage_recordDTO(1,120,1,1);

        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(u1));
        Mockito.when(machineRepository.findById(anyLong())).thenReturn(Optional.of(m1));
        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(gt1));
        Mockito.when(garbageRecordRepository.save(any(Garbage_record.class))).thenReturn(g1);

        ResponseEntity<Garbage_record> ResGarbageRecord =garbageRecordService.createGarbage_record(garbage_recordDTO);

        String jsonInput = this.convertToJson(ResGarbageRecord.getBody());
        String jsonOutput = this.convertToJson(g1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Create Pass");


    }

    @Test
    void patchGarbage_record() throws JsonProcessingException {
        Mockito.when(garbageRecordRepository.findById(anyLong())).thenReturn(Optional.of(g1));
        Mockito.when(garbageRecordRepository.save(any(Garbage_record.class))).thenReturn(g1);

        ResponseEntity<Garbage_record> ResGarbageRecord =garbageRecordService.patchGarbage_record(1L,g1);

        String jsonInput = this.convertToJson(ResGarbageRecord.getBody());
        String jsonOutput = this.convertToJson(g1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Patch By Id Pass");

    }

    @Test
    void updateGarbage_record() throws JsonProcessingException {
        Mockito.when(garbageRecordRepository.findById(anyLong())).thenReturn(Optional.of(g1));
        Mockito.when(garbageRecordRepository.save(any(Garbage_record.class))).thenReturn(g1);

        ResponseEntity<Garbage_record> ResGarbageRecord =garbageRecordService.updateGarbage_record(1L,g1);

        String jsonInput = this.convertToJson(ResGarbageRecord.getBody());
        String jsonOutput = this.convertToJson(g1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Put By Id Pass");
    }

    @Test
    void findAll() throws JsonProcessingException {
        List<Garbage_record> ListGarbageRecord =new ArrayList<>();
        ListGarbageRecord.add(g1);
        ListGarbageRecord.add(g2);
        ListGarbageRecord.add(g3);
        ListGarbageRecord.add(g4);
        Mockito.when(garbageRecordRepository.findAll()).thenReturn(asList(g1,g2,g3,g4));

        List<Garbage_record> ResGarbageRecord =garbageRecordService.findAll();

        String jsonInput = this.convertToJson(ResGarbageRecord);
        String jsonOutput = this.convertToJson(ListGarbageRecord);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Find All Pass");
    }

    @Test
    void deleteById() {
        garbageRecordService.deleteById(1L);
        verify(garbageRecordRepository).deleteById(anyLong());

        System.out.println("Test Delete By Id Pass");
    }

    private String convertToJson(Object bankAcct) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bankAcct);
    }
}