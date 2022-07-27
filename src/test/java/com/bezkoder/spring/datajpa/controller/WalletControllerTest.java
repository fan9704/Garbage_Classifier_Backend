package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.dto.WalletDTO;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.model.Wallet;
import com.bezkoder.spring.datajpa.service.WalletService;
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
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class WalletControllerTest {
    private MockMvc mockMvc;
    @Mock
    private WalletService walletService;
    @InjectMocks
    private WalletController walletController;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(walletController).build();
    }

    User u1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
    Wallet w1 = new Wallet(1,new BigDecimal(123),"Store",u1,null);

    @Test
    void getWalletRecordByUsername() throws Exception {
        String jsonInput = this.convertToJson(Arrays.asList(w1));
        Mockito.when(walletService.getWalletRecordByUsername(anyString())).thenReturn(new ResponseEntity<>(Arrays.asList(w1), HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/walletInfo/user1")
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
    void getWalletValueByUsername() throws Exception {
        String jsonInput = this.convertToJson(w1.getChange_value());
        Mockito.when(walletService.getWalletValueByUsername(anyString())).thenReturn(new ResponseEntity<>(w1.getChange_value(), HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/walletValue/user1")
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
    void createWalletRecord() throws Exception {
        WalletDTO w1DTO = new WalletDTO();
        w1DTO.setChange_value(new BigDecimal(123));
        w1DTO.setDescription("Store");
        w1DTO.setUsername("user1");
        String content = this.convertToJson(w1DTO);
        String jsonInput = this.convertToJson(Arrays.asList(w1));
        Mockito.when(walletService.createWalletRecord(any(WalletDTO.class))).thenReturn(new ResponseEntity<>(Arrays.asList(w1), HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/wallet")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(content)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Find Create Pass");
    }
    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}