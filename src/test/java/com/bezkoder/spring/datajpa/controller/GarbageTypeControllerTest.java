package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.service.GarbageTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.Http;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class GarbageTypeControllerTest {

//    @LocalServerPort
//    private int port;
//    private static final String BASE_URL ="http://localhost:";
//    @Autowired
//    private WebApplicationContext wac;

    private MockMvc mockMvc;
    @Mock
    private GarbageTypeService garbageTypeService;
    @InjectMocks
    private GarbageTypeController garbageTypeController;
//    @BeforeEach
//    void beforeEach() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(wac)
//                .alwaysDo(print())
//                .build();
//    }
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc=MockMvcBuilders.standaloneSetup(garbageTypeController).build();
    }

    Garbage_type g1= new Garbage_type(0,"寶特瓶",0.012);
    Garbage_type g2=new Garbage_type(1,"鐵鋁罐",0.2);
    Garbage_type g3=new Garbage_type(2,"紙類",0.003);
    Garbage_type g4=new Garbage_type(3,"鋁箔包",0.5);


    @Test
    void allGarbage_types() throws Exception {

        List garbageTypeList =new ArrayList<>(Arrays.asList(g1,g2,g3,g4));


        String jsonInput = this.convertToJson(garbageTypeList);


        Mockito.when(garbageTypeService.findAll()).thenReturn(garbageTypeList);
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/garbage_types")
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

        System.out.println("List Garbage Type Pass");
    }

    @Test
    void getGarbage_typeById() throws Exception {


        String jsonInput = this.convertToJson(g1);

        Mockito.when(garbageTypeService.findByGarbageTypeId(0)).thenReturn(g1);
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/garbage_type/0")
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
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Get Garbage Type By Id Pass");
    }

    @Test
    void createGarbage_type() throws Exception {
        Garbage_type g5=new Garbage_type(4,"電器",0.625);


        String jsonInput = this.convertToJson(g5);

        Mockito.when(garbageTypeService.createGarbage_type(g5)).thenReturn(new ResponseEntity<>(g5, HttpStatus.CREATED));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/garbage_type")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(jsonInput)
                )
//                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Create Garbage Type Pass");
    }

    @Test
    void patchGarbage_type() throws Exception {
        Garbage_type g5=new Garbage_type(4,"電器",0.625);
        String jsonInput = this.convertToJson(g5);

        Mockito.when(garbageTypeService.patchGarbage_type(0,g5)).thenReturn(new ResponseEntity<>(g5, HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/garbage_type/0")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(jsonInput)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Patch Garbage Type By Id Pass");
    }

    @Test
    void updateGarbage_type() throws Exception {
        Garbage_type g5=new Garbage_type(4,"電器",0.625);
        String jsonInput = this.convertToJson(g5);

        Mockito.when(garbageTypeService.patchGarbage_type(0,g5)).thenReturn(new ResponseEntity<>(g5,HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/garbage_type/0")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(jsonInput)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Patch Garbage Type By Id Pass");
    }

    @Test
    void delete() throws Exception {
        String jsonInput = this.convertToJson(g1);

        Mockito.when(garbageTypeService.findByGarbageTypeId(0)).thenReturn(g1);
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/garbage_type/0")
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
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Get Garbage Type By Id Pass");
    }

    private String convertToJson(Object bankAcct) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bankAcct);
    }
}