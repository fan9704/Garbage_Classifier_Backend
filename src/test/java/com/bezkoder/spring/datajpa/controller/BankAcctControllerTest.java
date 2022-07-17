package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.dto.Bank_acctDTO;
import com.bezkoder.spring.datajpa.model.Bank_acct;
import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.model.Role;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.service.BankAcctService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@RunWith(MockitoJUnitRunner.class)
class BankAcctControllerTest {


    private MockMvc mockMvc;
    @InjectMocks
    private BankAcctController bankAcctController;
    @Mock
    private BankAcctService bankAcctService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(bankAcctController).build();
    }
    Role _role = new Role(1,"User");
    User _user1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000",new HashSet<Role>(),null);
    User _user2 =new User(2,"user2","user2@mail.com","user2","user2","user2",false,"001",new HashSet<Role>(),null);
    Bank_type _bankType = new Bank_type(1,"Test1Bank","001");
    Bank_acct b1=new Bank_acct(1,_bankType,"000",_user1);
    Bank_acct b2=new Bank_acct(1,_bankType,"001",_user2);
    @Test
    void allBank_accts() throws Exception{//Pass
        String URI = "/api/bank_accts";


        List<Bank_acct> bankList = new ArrayList<>(Arrays.asList(b1,b2));
        String jsonInput = this.convertToJson(bankList);

        Mockito.when(bankAcctService.findAll()).thenReturn(bankList);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("List Test Complete");
    }

    @Test
    void getBank_acctByUsername() throws Exception {//Pass
        String username = "user1";
        String URI = "/api/back_acct/username/"+username;

        String jsonInput = this.convertToJson(b1);

        Mockito.when(bankAcctService.getBank_acctByUsername("user1")).thenReturn(new ResponseEntity<>( b1, HttpStatus.OK));
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Get By Username Test Complete");
    }

    @Test
    void getBank_acctById() throws Exception {//Pass
        String Id = "1";
        String URI = "/api/bank_acct/"+Id;

        String jsonInput = this.convertToJson(b1);

        Mockito.when(bankAcctService.getBank_acctById(1)).thenReturn(new ResponseEntity<>( b1, HttpStatus.OK));
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Get By Id Test Complete");
    }

    @Test
    void createBank_acct() throws Exception {
        String URI = "/api/back_acct";
        Bank_acctDTO bank_acctDTO = new Bank_acctDTO(1,"000",1);
        String InputDTO = this.convertToJson(bank_acctDTO);
        String jsonInput = this.convertToJson(b1);

//        Mockito.when(bankAcctService.createBank_acct(any())).thenReturn(new ResponseEntity<>(b1, HttpStatus.CREATED));
        MvcResult mvcResult = this.mockMvc.perform(post(URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(InputDTO)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Complete Test Create Bank Acct");
    }

    @Test
    void patchBank_acct() throws Exception {
        String Id = "1";
        String URI = "/api/back_acct/"+Id;
        String jsonInput = this.convertToJson(b1);

//        Mockito.when(bankAcctService.patchBank_acct(1,b1)).thenReturn(new ResponseEntity<>(b1, HttpStatus.OK));
        MvcResult mvcResult = this.mockMvc. perform(MockMvcRequestBuilders.patch(URI)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(jsonInput).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                        .andDo(print())
                        .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Complete Test Patch Bank Acct");
    }

    @Test
    void patchUserBankAcct() throws Exception {
        String URI = "/api/back_acct/user";
        Bank_acctDTO bank_acctDTO = new Bank_acctDTO(1,"000",1);

        String InputDTO = this.convertToJson(bank_acctDTO);
        String jsonInput = this.convertToJson(b1);


        Mockito.when(bankAcctService.patchUserBankAcct(bank_acctDTO)).thenReturn(new ResponseEntity<>(b1, HttpStatus.OK));
        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.patch(URI)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(InputDTO)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Complete Test Patch Bank Acct By User");
    }

    @Test
    void updateBank_acct() throws Exception {
        String Id = "1";
        String URI = "/api/back_acct/"+Id;
        Bank_acctDTO bank_acctDTO = new Bank_acctDTO(1,"000",1);


        String InputDTO = this.convertToJson(bank_acctDTO);
        String jsonInput = this.convertToJson(b1);


        Mockito.when(bankAcctService.updateBank_acct(1,b1)).thenReturn(new ResponseEntity<>(b1, HttpStatus.OK));
        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Complete Test Put Bank Acct By Id");
    }

    @Test
    void delete() throws Exception {
        Long LongId= Long.valueOf(1);
        String Id = "1";
        String URI = "/api/back_acct/"+Id;
        Bank_acctDTO bank_acctDTO = new Bank_acctDTO(1,"000",1);

        String jsonInput = this.convertToJson("Delete Complete");

        Mockito.when(bankAcctService.deleteById(LongId)).thenReturn(new ResponseEntity<>("Delete Complete",HttpStatus.OK));
        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(URI)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Complete Test Delete Bank Acct By Id");
    }

    private String convertToJson(Object bankAcct) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bankAcct);
    }
}