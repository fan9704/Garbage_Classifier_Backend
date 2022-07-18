package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.dto.Bank_acctDTO;
import com.bezkoder.spring.datajpa.model.Bank_acct;
import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.model.Role;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.BankAcctRepository;
import com.bezkoder.spring.datajpa.repository.BankTypeRepository;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BankAcctServiceTest {
    private MockMvc mockMvc;
    @InjectMocks
    private BankAcctService bankAcctService;
    @Mock
    private BankAcctRepository bankAcctRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BankTypeRepository bankTypeRepository;

    Role _role = new Role(1,"User");
    User _user1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
    User _user2 =new User(2,"user2","user2@mail.com","user2","user2","user2",false,"001", new HashSet<>(),null);
    Bank_type _bankType = new Bank_type(1,"Test1Bank","001");
    Bank_acct b1=new Bank_acct(1,_bankType,"000",_user1);
    Bank_acct b2=new Bank_acct(1,_bankType,"001",_user2);

    @Test
    void getBank_acctByUsername() throws JsonProcessingException {
        Mockito.when(userRepository.findByUserName(anyString())).thenReturn(_user1);
        Mockito.when(bankAcctRepository.findOneByUser(any(User.class))).thenReturn(b1);

        ResponseEntity<Bank_acct> _BankAcct = bankAcctService.getBank_acctByUsername("user1");

        String jsonInput = this.convertToJson(_BankAcct.getBody());
        String jsonOutput = this.convertToJson(b1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Id Pass");
    }

    @Test
    void getBank_acctById() throws JsonProcessingException {
        Mockito.when(bankAcctRepository.findById(anyLong())).thenReturn(Optional.of(b1));

        ResponseEntity<Bank_acct> _BankAcct = bankAcctService.getBank_acctById(1L);

        String jsonInput = this.convertToJson(_BankAcct.getBody());
        String jsonOutput = this.convertToJson(b1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Id Pass");
    }

    @Test
    void createBank_acct() throws JsonProcessingException {
        Bank_acctDTO bank_acctDTO = new Bank_acctDTO(1,"002",1);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(_user1));
        Mockito.when(bankAcctRepository.save(any(Bank_acct.class))).thenReturn(b1);
        Mockito.when(bankTypeRepository.findById(anyLong())).thenReturn(Optional.of(_bankType));

        ResponseEntity<Bank_acct> _BankAcct = bankAcctService.createBank_acct(bank_acctDTO);

        String jsonInput = this.convertToJson(_BankAcct.getBody());
        String jsonOutput = this.convertToJson(b1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Create BankAcct Pass");
    }

    @Test
    void patchBank_acct() throws JsonProcessingException {

        Mockito.when(bankAcctRepository.save(any(Bank_acct.class))).thenReturn(b1);
        Mockito.when(bankAcctRepository.findById(anyLong())).thenReturn(Optional.of(b1));

        ResponseEntity<Bank_acct> _BankAcct = bankAcctService.patchBank_acct(1L,b1);

        String jsonInput = this.convertToJson(_BankAcct.getBody());
        String jsonOutput = this.convertToJson(b1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Patch BankAcct Pass");
    }

    @Test
    void patchUserBankAcct() throws JsonProcessingException {
        Bank_acctDTO bank_acctDTO = new Bank_acctDTO(1,"002",1);

        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(_user1));
        Mockito.when(bankTypeRepository.findById(anyLong())).thenReturn(Optional.of(_bankType));
        Mockito.when(bankAcctRepository.findOneByUser(any(User.class))).thenReturn(b1);
        Mockito.when(bankAcctRepository.save(any(Bank_acct.class))).thenReturn(b1);

        ResponseEntity<Bank_acct> _BankAcct = bankAcctService.patchUserBankAcct(bank_acctDTO);

        String jsonInput = this.convertToJson(_BankAcct.getBody());
        String jsonOutput = this.convertToJson(b1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Patch BankAcct By User Pass");
    }

    @Test
    void updateBank_acct() throws JsonProcessingException {

        Mockito.when(bankAcctRepository.save(any(Bank_acct.class))).thenReturn(b1);
        Mockito.when(bankAcctRepository.findById(anyLong())).thenReturn(Optional.of(b1));

        ResponseEntity<Bank_acct> _BankAcct = bankAcctService.updateBank_acct(1L,b1);

        String jsonInput = this.convertToJson(_BankAcct.getBody());
        String jsonOutput = this.convertToJson(b1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Put BankAcct Pass");
    }

    @Test
    void findAll() throws JsonProcessingException {
        Mockito.when(bankAcctRepository.findAll()).thenReturn(Arrays.asList(b1,b2));
        List<Bank_acct> _BankAcctList= new ArrayList<>(Arrays.asList(b1,b2));
        List<Bank_acct> _ResBankAcctList =bankAcctService.findAll();


        String jsonInput = this.convertToJson(_ResBankAcctList);
        String jsonOutput = this.convertToJson(_BankAcctList);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test List BankAcct Pass");


    }

    @Test
    void deleteById() {
        bankAcctService.deleteById(1L);
        verify(bankAcctRepository).deleteById(1L);

        System.out.println("Test Delete By Id Pass");
    }
    private String convertToJson(Object bankAcct) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bankAcct);
    }
}