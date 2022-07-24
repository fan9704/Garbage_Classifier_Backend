package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.dto.Machine_storageDTO;
import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.model.Machine;
import com.bezkoder.spring.datajpa.model.Machine_storage;
import com.bezkoder.spring.datajpa.service.MachineStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class MachineStorageControllerTest {
    private MockMvc mockMvc;
    @Mock
    private MachineStorageService machineStorageService;
    @InjectMocks
    private MachineStorageController machineStorageController;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(machineStorageController).build();
    }
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
    void allMachine_storages() throws Exception {
        List<Machine_storage> ListMachineStorage = new ArrayList<>(Arrays.asList(m1,m2,m3,m4));

        Mockito.when(machineStorageService.findAll()).thenReturn(ListMachineStorage);

        String jsonInput = this.convertToJson(ListMachineStorage);
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/machines_storage")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("List All Pass");
    }

    @Test
    void getMachine_storageById() throws Exception {
        List<Machine_storage> ListMachineStorage = new ArrayList<>(Arrays.asList(m1,m2,m3,m4));
        String jsonInput = this.convertToJson(ListMachineStorage);
        Mockito.when(machineStorageService.getMachine_storageById(anyLong())).thenReturn(new ResponseEntity<>(m1, HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/machine_storage/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Find By Id Pass");
    }

    @Test
    void getMachine_storageByMachineId() throws Exception {
        List<Machine_storage> ListMachineStorage = new ArrayList<>(Arrays.asList(m1,m2,m3,m4));
        String jsonInput = this.convertToJson(ListMachineStorage);
        Mockito.when(machineStorageService.getMachine_storageByMachineId(anyLong())).thenReturn(ListMachineStorage);

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/machine_storage/machine/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Find By Machine Id Pass");
    }

    @Test
    void getMachine_storageByGarbageType() throws Exception {
        List<Machine_storage> ListMachineStorage = new ArrayList<>(Arrays.asList(m1,m2,m3,m4));
        String jsonInput = this.convertToJson(ListMachineStorage);
        Mockito.when(machineStorageService.getMachine_storageByGarbageType(anyLong())).thenReturn(ListMachineStorage);

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/machine_storage/garbage_type/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Find By GarbageType Id Pass");
    }

    @Test
    void createMachine_storage() throws Exception {
        Machine_storageDTO machine_storageDTO= new Machine_storageDTO(1,1,0.11);
        String jsonInput = this.convertToJson(m1);
        Mockito.when(machineStorageService.createMachine_storage(any(Machine_storageDTO.class))).thenReturn(new ResponseEntity<>(m1,HttpStatus.CREATED));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/machine_storage")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(this.convertToJson(machine_storageDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Create Pass");
    }

    @Test
    void patchMachine_storageWithJSON() throws Exception {
        Machine_storageDTO machine_storageDTO= new Machine_storageDTO(1,1,0.11);
        String jsonInput = this.convertToJson(m1);
        Mockito.when(machineStorageService.patchMachine_storageWithJSON(any(Machine_storageDTO.class))).thenReturn(new ResponseEntity<>(m1,HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/machine_storage")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(this.convertToJson(machine_storageDTO))
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Patch By DTO Pass");
    }

    @Test
    void patchMachine_storage() throws Exception {
        Machine_storageDTO machine_storageDTO= new Machine_storageDTO(1,1,0.11);
        String jsonInput = this.convertToJson(m1);
        Mockito.when(machineStorageService.patchMachine_storage(anyLong(),any(Machine_storageDTO.class))).thenReturn(new ResponseEntity<>(m1,HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/machine_storage/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(this.convertToJson(machine_storageDTO))
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Patch By Id Pass");
    }

    @Test
    void updateMachine_storage() throws Exception {
        Machine_storageDTO machine_storageDTO= new Machine_storageDTO(1,1,0.11);
        String jsonInput = this.convertToJson(m1);
        Mockito.when(machineStorageService.updateMachine_storage(anyLong(),any(Machine_storageDTO.class))).thenReturn(new ResponseEntity<>(m1,HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/machine_storage/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(this.convertToJson(machine_storageDTO))
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Put By Id Pass");
    }

    @Test
    void deleteMachine() throws Exception {
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/machine_storage/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        System.out.println("Delete By Id Pass");
    }
    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}