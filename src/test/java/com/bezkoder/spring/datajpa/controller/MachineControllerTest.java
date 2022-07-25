package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.model.Machine;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.model.Wallet;
import com.bezkoder.spring.datajpa.service.MachineService;
import com.bezkoder.spring.datajpa.service.MachineStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.HashSet;
@RunWith(MockitoJUnitRunner.class)
class MachineControllerTest {
    private MockMvc mockMvc;
    @Mock
    private MachineService machineService;
    @InjectMocks
    private MachineController machineController;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(machineController).build();
    }

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
    void getAllMachines() {
    }

    @Test
    void getAllMachinesByLocation() {
    }

    @Test
    void getMachineById() {
    }

    @Test
    void createMachine() {
    }

    @Test
    void updateMachinePicture() {
    }

    @Test
    void linkMachine() {
    }

    @Test
    void unlinkMachine() {
    }

    @Test
    void lockUserLink() {
    }

    @Test
    void updateRecycleRecord() {
    }

    @Test
    void unlockMachine() {
    }

    @Test
    void lockMachine() {
    }

    @Test
    void updateMachine() {
    }

    @Test
    void deleteMachine() {
    }
    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}