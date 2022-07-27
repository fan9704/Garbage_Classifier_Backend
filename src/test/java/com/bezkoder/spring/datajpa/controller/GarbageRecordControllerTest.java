package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.dto.Garbage_recordDTO;
import com.bezkoder.spring.datajpa.model.Garbage_record;
import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.model.Machine;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.service.GarbageRecordService;
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
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class GarbageRecordControllerTest {

    private MockMvc mockMvc;
    @Mock
    private GarbageRecordService garbageRecordService;
    @InjectMocks
    private GarbageRecordController garbageRecordController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(garbageRecordController).build();
    }
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
    void allGarbage_records() throws Exception {
        List<Garbage_record> ListGarbageRecord =new ArrayList<>();
        ListGarbageRecord.add(g1);
        ListGarbageRecord.add(g2);
        ListGarbageRecord.add(g3);
        ListGarbageRecord.add(g4);
        String jsonInput = this.convertToJson(ListGarbageRecord);

        Mockito.when(garbageRecordService.findAll()).thenReturn(ListGarbageRecord);
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/garbage_records")
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

        System.out.println("List Garbage Record Pass");
    }

    @Test
    void getGarbage_recordById() throws Exception {
        String jsonInput = this.convertToJson(g1);

        Mockito.when(garbageRecordService.getGarbage_recordById(anyLong())).thenReturn(new ResponseEntity<>(g1, HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/garbage_record/1")
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

        System.out.println("Get Garbage Record By Id Pass");
    }

    @Test
    void createGarbage_record() throws Exception {
        Garbage_recordDTO garbage_recordDTO = new Garbage_recordDTO(1,120,1,1);
        String jsonInput = convertToJson(garbage_recordDTO);

        Mockito.when(garbageRecordService.createGarbage_record(any(Garbage_recordDTO.class))).thenReturn(new ResponseEntity<>(g1,HttpStatus.CREATED));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/garbage_record")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(jsonInput)
                )
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();
        jsonInput = convertToJson(g1);
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Create Garbage Record Pass");
    }

    @Test
    void patchGarbage_record() throws Exception {

        String jsonInput = convertToJson(g1);

        Mockito.when(garbageRecordService.patchGarbage_record(anyLong(),any(Garbage_record.class))).thenReturn(new ResponseEntity<>(g1,HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/garbage_record/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(jsonInput)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();
        jsonInput = convertToJson(g1);
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Patch Garbage Record By Id Pass");
    }

    @Test
    void updateGarbage_record() throws Exception {
        String jsonInput = convertToJson(g1);

        Mockito.when(garbageRecordService.updateGarbage_record(anyLong(),any(Garbage_record.class))).thenReturn(new ResponseEntity<>(g1,HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/garbage_record/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(jsonInput)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();
        jsonInput = convertToJson(g1);
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Put Garbage Record By Id Pass");
    }

    @Test
    void delete() throws Exception {


        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/garbage_record/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();


        System.out.println("Delete Garbage Type By Id Pass");
    }
    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}