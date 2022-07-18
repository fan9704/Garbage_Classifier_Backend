package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.repository.BankTypeRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BankTypeServiceTest {
    private MockMvc mockMvc;
    @InjectMocks
    private BankTypeService bankTypeService;
    @Mock
    private BankTypeRepository bankTypeRepository;

    Bank_type b1=new Bank_type(1,"中央銀行","001");
    Bank_type b2=new Bank_type(2,"台灣銀行","002");
    Bank_type b3=new Bank_type(3,"土地銀行","003");
    Bank_type b4=new Bank_type(4,"玉山銀行","004");
    @Test
    void findAll() throws JsonProcessingException {
        List<Bank_type> _bankTypeList = new ArrayList<>(Arrays.asList(b1,b2,b3,b4));

        Mockito.when(bankTypeRepository.findAll()).thenReturn(Arrays.asList(b1,b2,b3,b4));

        List<Bank_type> _ResBankType = bankTypeService.findAll();

        String jsonInput = this.convertToJson(_ResBankType);
        String jsonOutput = this.convertToJson(_bankTypeList);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Find All Pass");

    }

    @Test
    void getBank_typeById() throws JsonProcessingException {
        Mockito.when(bankTypeRepository.findById(anyLong())).thenReturn(Optional.of(b2));

        ResponseEntity<Bank_type> _BankType= bankTypeService.getBank_typeById(2L);

        String jsonInput = this.convertToJson(_BankType.getBody());
        String jsonOutput = this.convertToJson(b2);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Id Pass");
    }

    @Test
    void createBank_type() throws JsonProcessingException {
        Bank_type b5=new Bank_type(5,"華南銀行","005");

        Mockito.when(bankTypeRepository.save(Mockito.any(Bank_type.class))).thenReturn(b5);
        ResponseEntity<Bank_type> _ResBankType= bankTypeService.createBank_type(b5);
        String jsonInput = this.convertToJson(b5);
        String jsonOutput = this.convertToJson(_ResBankType.getBody());

        System.out.println("Status Code :"+_ResBankType.getStatusCode());
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
    }

    @Test
    void patchBank_type() throws JsonProcessingException {
        Bank_type b5=new Bank_type(5,"華南銀行","005");

        Mockito.when(bankTypeRepository.findById(anyLong())).thenReturn(Optional.of(b5));
        Mockito.when(bankTypeRepository.save(Mockito.any(Bank_type.class))).thenReturn(b5);
        ResponseEntity<Bank_type> _ResBankType= bankTypeService.patchBank_type(1L,b5);
        String jsonInput = this.convertToJson(b5);
        String jsonOutput = this.convertToJson(_ResBankType.getBody());

        System.out.println("Status Code :"+_ResBankType.getStatusCode());
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Patch BankType By Id Pass");
    }

    @Test
    void updateBank_type() throws JsonProcessingException {
        Bank_type b5=new Bank_type(5,"華南銀行","005");

        Mockito.when(bankTypeRepository.findById(anyLong())).thenReturn(Optional.of(b5));
        Mockito.when(bankTypeRepository.save(Mockito.any(Bank_type.class))).thenReturn(b5);
        ResponseEntity<Bank_type> _ResBankType= bankTypeService.updateBank_type(1L,b5);
        String jsonInput = this.convertToJson(b5);
        String jsonOutput = this.convertToJson(_ResBankType.getBody());

        System.out.println("Status Code :"+_ResBankType.getStatusCode());
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Put BankType By Id Pass");
    }

    @Test
    void count() {
       Long _count = bankTypeService.count();
       verify(bankTypeRepository).count();

        System.out.println("Test Count BankType Pass");
    }

    @Test
    void deleteById() {
        bankTypeService.deleteById(1L);
        verify(bankTypeRepository).deleteById(1L);

        System.out.println("Test Delete By Id Pass");
    }

    private String convertToJson(Object bankAcct) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bankAcct);
    }
}