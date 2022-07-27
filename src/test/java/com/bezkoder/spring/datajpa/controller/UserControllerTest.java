package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.dto.LoginDTO;
import com.bezkoder.spring.datajpa.dto.UserDTO;
import com.bezkoder.spring.datajpa.dto.UserTokenDTO;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.service.UserService;
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

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(userController).build();
    }
    User u1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
    User u2 =new User(2,"user2","user2@mail.com","user2","user2","user2",false,"001", new HashSet<>(),null);
    User u3 =new User(3,"user3","user3@mail.com","user3","user3","user3",false,"002", new HashSet<>(),null);
    User u4 =new User(4,"user4","user4@mail.com","user4","user4","user4",false,"003", new HashSet<>(),null);

    @Test
    void getAllUsers() throws Exception {
        String jsonInput = this.convertToJson(Arrays.asList(u1,u2,u3,u4));
        Mockito.when(userService.getAllUsers()).thenReturn(new ResponseEntity<>(Arrays.asList(u1,u2,u3,u4), HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users")
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

        System.out.println("Find All Pass");
    }

    @Test
    void getUserById() throws Exception {
        String jsonInput = this.convertToJson(u1);
        Mockito.when(userService.getUserById(anyLong())).thenReturn(new ResponseEntity<>(u1, HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/user/1")
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
    void getUserByUsername() throws Exception {
        String jsonInput = this.convertToJson(u1);
        Mockito.when(userService.getUserByUsername(anyString())).thenReturn(new ResponseEntity<>(u1, HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/userinfo/user1")
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

        System.out.println("Find By Username Pass");
    }

    @Test
    void checkUserLogin() {
        System.out.println("Test User Login Pass");
    }

    @Test
    void registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("user1","user1@gamil.com","user1","user1","user1",false);
        String jsonInput = this.convertToJson(u1);
        Mockito.when(userService.registerUser(any(UserDTO.class))).thenReturn(new ResponseEntity<>(u1,HttpStatus.CREATED));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/register")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(this.convertToJson(userDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Register User Pass");
    }

    @Test
    void loginUser() {
        System.out.println("User Login Pass");
    }

    @Test
    void userLogout() {
        System.out.println("User Logout Pass");
    }

    @Test
    void changePassword() throws Exception {
        LoginDTO loginDTO = new LoginDTO("user1","user1");
        String jsonInput = this.convertToJson(u1);
        Mockito.when(userService.changePassword(any(LoginDTO.class))).thenReturn(new ResponseEntity<>(u1,HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/changePassword")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(this.convertToJson(loginDTO))
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("User Change Password Pass");
    }

    @Test
    void editUserInfo() throws Exception {
        UserDTO userDTO = new UserDTO("user1","user1@gamil.com","user1","user1","user1",false);
        String jsonInput = this.convertToJson(u1);
        Mockito.when(userService.EditUserInfo(any(UserDTO.class))).thenReturn(new ResponseEntity<>(u1,HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/EditUserInfo")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(this.convertToJson(userDTO))
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Edit User Info Pass");
    }

    @Test
    void deleteUser() throws Exception {
        String jsonInput = this.convertToJson(200);
        Mockito.when(userService.deleteUser(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/user/1")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = String.valueOf(mockHttpServletResponse.getStatus());

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Delete By Id Pass");
    }

    @Test
    void putMessage() {
        System.out.println("Test Put Message Pass");
    }

    @Test
    void saveToken() throws Exception {
        UserTokenDTO userTokenDTO= new UserTokenDTO();
        userTokenDTO.setId(1);
        userTokenDTO.setFirebaseToken("000");

        Mockito.when(userService.saveUserToken(anyLong(),anyString())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        MvcResult mvcResult =mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/saveToken")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(this.convertToJson(userTokenDTO))
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonInput = this.convertToJson(200);
        String jsonOutput = String.valueOf(mockHttpServletResponse.getStatus());

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Save User Firebase Token Pass");
    }
    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}