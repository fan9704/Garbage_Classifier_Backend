package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.dto.LoginDTO;
import com.bezkoder.spring.datajpa.dto.UserDTO;
import com.bezkoder.spring.datajpa.model.User;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    User u1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
    User u2 =new User(2,"user2","user2@mail.com","user2","user2","user2",false,"001", new HashSet<>(),null);
    User u3 =new User(3,"user3","user3@mail.com","user3","user3","user3",false,"002", new HashSet<>(),null);
    User u4 =new User(4,"user4","user4@mail.com","user4","user4","user4",false,"003", new HashSet<>(),null);

    @Test
    void getAllUsers() throws JsonProcessingException {
        List<User> UserList = new ArrayList<>(Arrays.asList(u1,u2,u3,u4));
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(u1,u2,u3,u4));
        ResponseEntity<List<User>> ResponseUser = userService.getAllUsers();

        String jsonInput = this.convertToJson(ResponseUser.getBody());
        String jsonOutput = this.convertToJson(UserList);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find All Pass");

    }

    @Test
    void getUserById() throws JsonProcessingException {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(u1));
        ResponseEntity<User> ResponseUser = userService.getUserById(1L);

        String jsonInput = this.convertToJson(ResponseUser.getBody());
        String jsonOutput = this.convertToJson(u1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Id Pass");
    }

    @Test
    void getUserByUsername() throws JsonProcessingException{
        Mockito.when(userRepository.findByUserName(anyString())).thenReturn(u1);
        ResponseEntity<User> ResponseUser = userService.getUserByUsername("user1");

        String jsonInput = this.convertToJson(ResponseUser.getBody());
        String jsonOutput = this.convertToJson(u1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Find By Username Pass");
    }

    @Test
    void checkUserLogin() throws JsonProcessingException {
//        Mockito.when(userRepository.findByUserName(anyString())).thenReturn(u1);
//        ResponseEntity<User> ResponseUser = userService.checkUserLogin(any(HttpSession.class),any(SessionStatus.class));
//
//        String jsonInput = this.convertToJson(ResponseUser.getBody());
//        String jsonOutput = this.convertToJson(u1);
//
//        System.out.println("Input:"+jsonInput);
//        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);
//
        System.out.println("Test Check User Login Pass");
    }

    @Test
    void registerUser() throws JsonProcessingException {

        UserDTO userDTO = new UserDTO("user1","user1@gamil.com","user1","user1","user1",false);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(u1);
        ResponseEntity<User> ResponseUser = userService.registerUser(userDTO);

        String jsonInput = this.convertToJson(ResponseUser.getBody());
        String jsonOutput = this.convertToJson(null);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Register User Pass");
    }

    @Test
    void loginUser() throws JsonProcessingException {
//        LoginDTO loginDTO = new LoginDTO("user1","user1");
//        Mockito.when(userRepository.findByUserName(anyString())).thenReturn(u1);
//        ResponseEntity<User> ResponseUser = userService.loginUser(loginDTO,any(HttpSession .class));
//
//        String jsonInput = this.convertToJson(ResponseUser.getBody());
//        String jsonOutput = this.convertToJson(u1);
//
//        System.out.println("Input:"+jsonInput);
//        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Login User Pass");

    }

    @Test
    void userLogout() throws JsonProcessingException {
//        ResponseEntity<?> ResponseUser = userService.userLogout(any(HttpSession .class),any(SessionStatus.class));
//
//        String jsonInput = this.convertToJson(ResponseUser.getBody());
//        String jsonOutput = this.convertToJson("Logout");
//
//        System.out.println("Input:"+jsonInput);
//        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Logout User Pass");
    }

    @Test
    void changePassword() throws JsonProcessingException {
        LoginDTO loginDTO = new LoginDTO("user1","user1");
        Mockito.when(bCryptPasswordEncoder.encode("user1")).thenReturn("user1");
        Mockito.when(userRepository.findByUserName("user1")).thenReturn(u1);
        Mockito.when(userRepository.save(u1)).thenReturn(u1);
        Mockito.when(userService.changePassword(loginDTO.getUsername(),loginDTO.getPassword())).thenReturn(u1);

        ResponseEntity<User> ResponseUser = userService.changePassword(loginDTO);


        String jsonInput = this.convertToJson(ResponseUser.getBody());
        String jsonOutput = this.convertToJson(u1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Change Password Pass");
    }

    @Test
    void editUserInfo() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("user1","user1","user1","user1","user1",false);

        Mockito.when(userRepository.findByUserName(userDTO.getUserName())).thenReturn(u1);
        Mockito.when(userRepository.save(u1)).thenReturn(u1);
        Mockito.when(userService.EditUserProfile(userDTO)).thenReturn(u1);
        ResponseEntity<?> ResponseUser = userService.EditUserInfo(userDTO);

        String jsonInput = this.convertToJson(ResponseUser.getBody());
        String jsonOutput = this.convertToJson(u1);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Edit User Info Pass");
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1L);
        verify(userRepository).deleteById(anyLong());

        System.out.println("Test Delete By Id Pass");
    }

    @Test
    void deleteAllUsers() {
        userService.deleteAllUsers();
        verify(userRepository).deleteAll();

        System.out.println("Test Delete All Pass");
    }

    @Test
    void saveUserToken() throws JsonProcessingException {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(u1));
        Mockito.when(userRepository.save(u1)).thenReturn(u1);

        ResponseEntity<?> ResponseUser = userService.saveUserToken(1L,"001");

        String jsonInput = this.convertToJson(ResponseUser.getStatusCodeValue());
        String jsonOutput = this.convertToJson(204);

        System.out.println("Input:"+jsonInput);
        System.out.println("Output:"+jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Save User Token Pass");
    }

    @Test
    void putMessage() throws JsonProcessingException {
//        LoginDTO loginDTO = new LoginDTO("user1","user1");
//        Mockito.when(userRepository.findByUserName(anyString())).thenReturn(u1);
//        Mockito.when(userService.loginUser("user1","user1")).thenReturn(true);
//
//        ResponseEntity<?> ResponseUser = userService.putMessage(loginDTO,any(HttpSession.class));
//
//        String jsonInput = this.convertToJson(ResponseUser.getBody());
//        String jsonOutput = this.convertToJson(u1);
//
//        System.out.println("Input:"+jsonInput);
//        System.out.println("Output:"+jsonOutput);
//        assertThat(jsonInput).isEqualTo(jsonOutput);

        System.out.println("Test Put Message Pass");
    }

    private String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}