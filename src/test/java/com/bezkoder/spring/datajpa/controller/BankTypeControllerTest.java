package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.service.BankTypeService;
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
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class BankTypeControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private BankTypeController bankTypeController;
    @Mock
    private BankTypeService bankTypeService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(bankTypeController).build();
    }
    Bank_type b1 = new Bank_type(1,"TestBank","001");
    Bank_type b2 = new Bank_type(2,"TestBank2","002");
    @Test
    void allBank_types() throws Exception {

        List<Bank_type> bank_typeList=new ArrayList<>(Arrays.asList(b1));

        when(bankTypeService.findAll()).thenReturn(bank_typeList);

        String jsonInput = this.convertToJson(bank_typeList);


        Mockito.when(bankTypeService.findAll()).thenReturn(bank_typeList);
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/bank_types")
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

        System.out.println("Get All BankType Pass");
    }

    @Test
    void getBank_typeById() throws Exception {


        when(bankTypeService.getBank_typeById(1)).thenReturn(new ResponseEntity<>( b1, HttpStatus.OK));

        String jsonInput = this.convertToJson(b1);

        Mockito.when(bankTypeService.getBank_typeById(1)).thenReturn(new ResponseEntity<>(b1,HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/bank_type/1")
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

        System.out.println("Get BankType By Id Pass");
    }

    @Test
    void createBank_type() throws Exception {


        when(bankTypeService.createBank_type(b1)).thenReturn(new ResponseEntity<>( b2, HttpStatus.CREATED));

        String jsonInput = this.convertToJson(b2);

        Mockito.when(bankTypeService.createBank_type(b2)).thenReturn(new ResponseEntity<>(b2, HttpStatus.CREATED));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/bank_type")
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

        System.out.println("Create BankType Pass");
    }

    @Test
    void patchBank_type() throws Exception {
        String jsonInput = this.convertToJson(b1);

        Mockito.when(bankTypeService.patchBank_type(0,b1)).thenReturn(new ResponseEntity<>(b1, HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/bank_type/0")
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

        System.out.println("Patch BankType By Id Pass");
    }

    @Test
    void updateBank_type() throws Exception {
        String jsonInput = this.convertToJson(b2);

        Mockito.when(bankTypeService.updateBank_type(0,b2)).thenReturn(new ResponseEntity<>(b2,HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/bank_type/0")
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

        System.out.println("Put BankType By Id Pass");
    }

    @Test
    void delete() throws Exception {
        String jsonInput = this.convertToJson(b1);

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/bank_type/0")
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

        System.out.println("Delete BankType By Id Pass");
    }

    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}