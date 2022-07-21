package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.dto.Transfer_money_recordDTO;
import com.bezkoder.spring.datajpa.model.Transfer_money_record;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.TransferMoneyRecordRepository;
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

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransferMoneyRecordServiceTest {
    private MockMvc mockMvc;
    @InjectMocks
    private TransferMoneyRecordService transferMoneyRecordService;
    @Mock
    private TransferMoneyRecordRepository transferMoneyRecordRepository;
    @Mock
    private UserRepository userRepository;

    User u1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
    User u2 =new User(2,"user2","user2@mail.com","user2","user2","user2",false,"001", new HashSet<>(),null);
    Transfer_money_record t1 = new Transfer_money_record(1,u1,new BigDecimal(120),null,"華南銀行");
    Transfer_money_record t2 = new Transfer_money_record(2,u2,new BigDecimal(240),null,"土地銀行");
    Transfer_money_record t3 = new Transfer_money_record(3,u1,new BigDecimal(360),null,"玉山銀行");
    Transfer_money_record t4 = new Transfer_money_record(4,u2,new BigDecimal(480),null,"中央銀行");

    @Test
    void getTransfer_money_recordById() throws Exception{
        Mockito.when(transferMoneyRecordRepository.findById(anyLong())).thenReturn(Optional.of(t1));
        ResponseEntity<Transfer_money_record> ResTransferMoneyRecord = transferMoneyRecordService.getTransfer_money_recordById(1L);

        String jsonInput = this.convertToJson(ResTransferMoneyRecord.getBody());
        String jsonOutput = this.convertToJson(t1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Id Pass");
    }

    @Test
    void createTransfer_money_record() throws Exception {
        Transfer_money_recordDTO transfer_money_recordDTO = new Transfer_money_recordDTO(1,new BigDecimal(120),"華南銀行");
        Mockito.when(transferMoneyRecordRepository.save(any(Transfer_money_record.class))).thenReturn(t1);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(u1));

        ResponseEntity<Transfer_money_record> ResTransferMoneyRecord = transferMoneyRecordService.createTransfer_money_record(transfer_money_recordDTO);

        String jsonInput = this.convertToJson(ResTransferMoneyRecord.getBody());
        String jsonOutput = this.convertToJson(t1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Create Pass");
    }

    @Test
    void patchTransfer_money_record() throws Exception{
        Transfer_money_recordDTO transfer_money_recordDTO = new Transfer_money_recordDTO(1,new BigDecimal(120),"華南銀行");
        Mockito.when(transferMoneyRecordRepository.findById(anyLong())).thenReturn(Optional.of(t1));
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(u1));
        Mockito.when(transferMoneyRecordRepository.save(any(Transfer_money_record.class))).thenReturn(t1);

        ResponseEntity<Transfer_money_record> ResTransferMoneyRecord = transferMoneyRecordService.patchTransfer_money_record(1L,transfer_money_recordDTO);

        String jsonInput = this.convertToJson(ResTransferMoneyRecord.getBody());
        String jsonOutput = this.convertToJson(t1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Patch By Id Pass");
    }

    @Test
    void updateTransfer_money_record() throws Exception{
        Transfer_money_recordDTO transfer_money_recordDTO = new Transfer_money_recordDTO(1,new BigDecimal(120),"華南銀行");
        Mockito.when(transferMoneyRecordRepository.findById(anyLong())).thenReturn(Optional.of(t1));
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(u1));
        Mockito.when(transferMoneyRecordRepository.save(any(Transfer_money_record.class))).thenReturn(t1);

        ResponseEntity<Transfer_money_record> ResTransferMoneyRecord = transferMoneyRecordService.updateTransfer_money_record(1L,transfer_money_recordDTO);

        String jsonInput = this.convertToJson(ResTransferMoneyRecord.getBody());
        String jsonOutput = this.convertToJson(t1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Update By Id Pass");
    }

    @Test
    void findAll() throws Exception{
        List<Transfer_money_record> ListTransferMoneyRecord = new ArrayList<>(Arrays.asList(t1,t2,t3,t4));

        Mockito.when(transferMoneyRecordRepository.findAll()).thenReturn(ListTransferMoneyRecord);

       List<Transfer_money_record> ResTransferMoneyRecord = transferMoneyRecordService.findAll();

        String jsonInput = this.convertToJson(ResTransferMoneyRecord);
        String jsonOutput = this.convertToJson(ListTransferMoneyRecord);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find All Pass");
    }

    @Test
    void deleteById() throws Exception{
        transferMoneyRecordService.deleteById(1L);
        verify(transferMoneyRecordRepository).deleteById(anyLong());

        System.out.println("Test Delete By Id Pass");
    }
    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}