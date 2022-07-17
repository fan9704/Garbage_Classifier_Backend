package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.repository.GarbageTypeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class GarbageTypeServiceTest {

    private MockMvc mockMvc;
    @InjectMocks
    private GarbageTypeService garbageTypeService;
    @Mock
    private GarbageTypeRepository garbageTypeRepository;

//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc= MockMvcBuilders.standaloneSetup(garbageTypeService).build();
//    }
    Garbage_type g1= new Garbage_type(0,"寶特瓶",0.012);
    Garbage_type g2=new Garbage_type(1,"鐵鋁罐",0.2);
    Garbage_type g3=new Garbage_type(2,"紙類",0.003);
    Garbage_type g4=new Garbage_type(3,"鋁箔包",0.5);
    @Test
    void getGarbage_typeById() throws JsonProcessingException {//Pass
        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(g1));

        ResponseEntity<Garbage_type> _ResGarbageType= garbageTypeService.getGarbage_typeById(0);
        Garbage_type _garbageType =_ResGarbageType.getBody();
        String jsonInput = this.convertToJson(g1);
        String jsonOutput = this.convertToJson(_garbageType);

        System.out.println("Status Code :"+_ResGarbageType.getStatusCode());
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
    }

    @Test
    void createGarbage_type() throws JsonProcessingException {//Pass
        Garbage_type g5=new Garbage_type(4,"廚餘",0.15);

        Mockito.when(garbageTypeRepository.save(Mockito.any(Garbage_type.class))).thenReturn(g5);
        ResponseEntity<Garbage_type> _ResGarbageType= garbageTypeService.createGarbage_type(g5);
        Garbage_type _garbageType =_ResGarbageType.getBody();
        String jsonInput = this.convertToJson(g5);
        String jsonOutput = this.convertToJson(_garbageType);

        System.out.println("Status Code :"+_ResGarbageType.getStatusCode());
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
    }

    @Test
    void patchGarbage_type() throws JsonProcessingException {
        Garbage_type MockType = mock(Garbage_type.class);
        Garbage_type g5=new Garbage_type(4,"廚餘",0.15);

        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(g5));
        Mockito.when(garbageTypeRepository.save(Mockito.any(Garbage_type.class))).thenReturn(g5);

        ResponseEntity<Garbage_type> _ResGarbageType= garbageTypeService.patchGarbage_type(4L,g5);
        Garbage_type _garbageType =_ResGarbageType.getBody();
        String jsonInput = this.convertToJson(g5);
        String jsonOutput = this.convertToJson(_garbageType);

        System.out.println("Status Code :"+_ResGarbageType.getStatusCode());
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Patch By Id Pass");
    }

    @Test
    void updateGarbage_type() throws JsonProcessingException {
        Garbage_type g5=new Garbage_type(4,"廚餘",0.15);

        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(g5));
        Mockito.when(garbageTypeRepository.save(Mockito.any(Garbage_type.class))).thenReturn(g5);

        ResponseEntity<Garbage_type> _ResGarbageType= garbageTypeService.updateGarbage_type(4L,g5);
        Garbage_type _garbageType =_ResGarbageType.getBody();
        String jsonInput = this.convertToJson(g5);
        String jsonOutput = this.convertToJson(_garbageType);

        System.out.println("Status Code :"+_ResGarbageType.getStatusCode());
        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Put By Id Pass");
    }

    @Test
    void findAll() throws JsonProcessingException {//Pass

        List<Garbage_type> _garbageTypeList = new ArrayList<>(Arrays.asList(g1,g2,g3,g4));

        Mockito.when(garbageTypeRepository.findAll()).thenReturn(asList(g1,g2,g3,g4));

        List<Garbage_type> _ResGarbageType= garbageTypeService.findAll();

        String jsonInput = this.convertToJson(_ResGarbageType);
        String jsonOutput = this.convertToJson(_garbageTypeList);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
        System.out.println("Test Find All Pass");
    }

    @Test
    void deleteById() {
        garbageTypeService.deleteById(0L);//Pass
        verify(garbageTypeRepository).deleteById(anyLong());

        System.out.println("Test Delete By Id Pass");
    }

    @Test
    void findByGarbageTypeId() throws JsonProcessingException {//Pass

        Mockito.when(garbageTypeRepository.findById(anyLong())).thenReturn(Optional.of(g2));

        Garbage_type _GarbageType= garbageTypeService.findByGarbageTypeId(1L);

        String jsonInput = this.convertToJson(_GarbageType);
        String jsonOutput = this.convertToJson(g2);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Id Pass");
    }
    private String convertToJson(Object bankAcct) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bankAcct);
    }
}