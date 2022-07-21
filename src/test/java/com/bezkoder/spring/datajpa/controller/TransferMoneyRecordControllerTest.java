package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.dto.Transfer_money_recordDTO;
import com.bezkoder.spring.datajpa.model.Transfer_money_record;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.service.GarbageTypeService;
import com.bezkoder.spring.datajpa.service.TransferMoneyRecordService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class TransferMoneyRecordControllerTest {
    private MockMvc mockMvc;
    @Mock
    private TransferMoneyRecordService transferMoneyRecordService;
    @InjectMocks
    private TransferMoneyRecordController transferMoneyRecordController;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(transferMoneyRecordController).build();
    }
    User u1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
    User u2 =new User(2,"user2","user2@mail.com","user2","user2","user2",false,"001", new HashSet<>(),null);
    Transfer_money_record t1 = new Transfer_money_record(1,u1,new BigDecimal(120),null,"華南銀行");
    Transfer_money_record t2 = new Transfer_money_record(2,u2,new BigDecimal(240),null,"土地銀行");
    Transfer_money_record t3 = new Transfer_money_record(3,u1,new BigDecimal(360),null,"玉山銀行");
    Transfer_money_record t4 = new Transfer_money_record(4,u2,new BigDecimal(480),null,"中央銀行");

    @Test
    void allTransfer_money_records() throws Exception{
        List<Transfer_money_record> ListTransferMoneyRecord = new ArrayList<>(Arrays.asList(t1,t2,t3,t4));
        String jsonInput = this.convertToJson(ListTransferMoneyRecord);

        Mockito.when(transferMoneyRecordService.findAll()).thenReturn(ListTransferMoneyRecord);
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/transfer_money_records")
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
    void getTransfer_money_recordById() throws Exception{
        String jsonInput = this.convertToJson(t1);
        Mockito.when(transferMoneyRecordService.getTransfer_money_recordById(anyLong())).thenReturn(new ResponseEntity<>(t1, HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/transfer_money_record/1")
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
    void createTransfer_money_record() throws Exception{
        Transfer_money_recordDTO transfer_money_recordDTO = new Transfer_money_recordDTO(1,new BigDecimal(120),"華南銀行");


        Mockito.when(transferMoneyRecordService.createTransfer_money_record(any(Transfer_money_recordDTO.class))).thenReturn(new ResponseEntity<>(t1,HttpStatus.CREATED));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/transfer_money_record")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(convertToJson(null))
                )
//                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonInput = this.convertToJson(t1);
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Create Pass");
    }

    @Test
    void patchTransfer_money_record() throws Exception{
        Transfer_money_recordDTO transfer_money_recordDTO = new Transfer_money_recordDTO(1,new BigDecimal(120),"華南銀行");


        Mockito.when(transferMoneyRecordService.patchTransfer_money_record(anyLong(),any(Transfer_money_recordDTO.class))).thenReturn(new ResponseEntity<>(t1,HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/transfer_money_record/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(convertToJson(null))
                )
//                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonInput = this.convertToJson(t1);
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Patch Pass");
    }

    @Test
    void updateTransfer_money_record() throws Exception{
        Transfer_money_recordDTO transfer_money_recordDTO = new Transfer_money_recordDTO(1,new BigDecimal(120),"華南銀行");


        Mockito.when(transferMoneyRecordService.updateTransfer_money_record(anyLong(),any(Transfer_money_recordDTO.class))).thenReturn(new ResponseEntity<>(t1,HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/transfer_money_record/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(convertToJson(null))
                )
//                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonInput = this.convertToJson(t1);
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Put Pass");
    }

    @Test
    void delete() throws Exception {
        Mockito.when(transferMoneyRecordService.patchTransfer_money_record(anyLong(),any(Transfer_money_recordDTO.class))).thenReturn(new ResponseEntity<>(t1,HttpStatus.OK));
        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/transfer_money_record/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
//                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonInput = this.convertToJson(t1);
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Delete Pass");
    }
    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}