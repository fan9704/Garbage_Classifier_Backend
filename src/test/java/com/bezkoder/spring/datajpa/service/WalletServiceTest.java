package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.dto.WalletDTO;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.model.Wallet;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import com.bezkoder.spring.datajpa.repository.WalletRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {
    @InjectMocks
    private WalletService walletService;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private UserRepository userRepository;

    User u1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
    Wallet w1 = new Wallet(1,new BigDecimal(123),"Store",u1,null);

    @Test
    void getWalletRecordByUsername() throws JsonProcessingException {
        List<Wallet> ListWallet = new ArrayList<>(Arrays.asList(w1));
        Mockito.when(userRepository.findByUserName(anyString())).thenReturn(u1);
        Mockito.when(walletRepository.findAllByUserid(anyLong())).thenReturn(ListWallet);

        ResponseEntity<List<Wallet>> ResWallet =walletService.getWalletRecordByUsername("user1");

        String jsonInput = this.convertToJson(ResWallet.getBody());
        String jsonOutput = this.convertToJson(ListWallet);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Username Pass");

    }

    @Test
    void getWalletValueByUsername() throws JsonProcessingException {

        Mockito.when(userRepository.findByUserName(anyString())).thenReturn(u1);
        Mockito.when(walletRepository.getCurrentCash(anyInt())).thenReturn(w1.getChange_value());

        ResponseEntity<BigDecimal> ResWallet =walletService.getWalletValueByUsername("user1");

        String jsonInput = this.convertToJson(ResWallet.getBody());
        String jsonOutput = this.convertToJson(w1.getChange_value());

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Username Pass");
    }

    @Test
    void createWalletRecord() throws JsonProcessingException {
        WalletDTO w1DTO = new WalletDTO();
        w1DTO.setChange_value(new BigDecimal(123));
        w1DTO.setDescription("Store");
        w1DTO.setUsername("user1");

        Mockito.when(userRepository.findByUserName(anyString())).thenReturn(u1);
        Mockito.when(walletRepository.save(any(Wallet.class))).thenReturn(w1);
        Mockito.when(walletRepository.findAllByUserid(anyLong())).thenReturn(Arrays.asList(w1));

        ResponseEntity<List<Wallet>> ResListWallet=walletService.createWalletRecord(w1DTO);

        String jsonInput = this.convertToJson(ResListWallet.getBody());
        String jsonOutput = this.convertToJson(Arrays.asList(w1));

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Create Pass");


    }

    @Test
    void findAll() throws JsonProcessingException {
        Mockito.when(walletRepository.findAll()).thenReturn(Arrays.asList(w1,w1));

        List<Wallet> ResListWallet=walletService.findAll();

        String jsonInput = this.convertToJson(ResListWallet);
        String jsonOutput = this.convertToJson(Arrays.asList(w1,w1));

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find All Pass");
    }

    @Test
    void deleteById() {
        walletService.deleteById(1L);
        verify(walletRepository).deleteById(anyLong());

        System.out.println("Test Delete By Id Pass");
    }
    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}