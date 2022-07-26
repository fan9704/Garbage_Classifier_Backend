package com.bezkoder.spring.datajpa.controller;

import java.util.List;

import com.bezkoder.spring.datajpa.dto.LoginDTO;
import com.bezkoder.spring.datajpa.dto.UserDTO;
import com.bezkoder.spring.datajpa.dto.UserTokenDTO;
import com.bezkoder.spring.datajpa.model.*;
import com.bezkoder.spring.datajpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
      return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/userinfo/{username}")
    public ResponseEntity<User> getUserById(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }
    @GetMapping("/checkLogin")
    public ResponseEntity<User> checkUserLogin(HttpSession session, SessionStatus sessionStatus){
        return userService.checkUserLogin(session,sessionStatus);
    }
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginDTO loginDTO, HttpSession session) {
        return userService.loginUser(loginDTO,session);
    }
    @GetMapping("/logout")
    public ResponseEntity<String> userLogout(HttpSession session, SessionStatus sessionStatus){
        return userService.userLogout(session,sessionStatus);
    }
    @PutMapping("/changePassword")
    public ResponseEntity<User> changePassword(@RequestBody LoginDTO loginDTO){
        return  userService.changePassword(loginDTO);
    }
    @PutMapping("/EditUserInfo")
    public ResponseEntity<User> EditUserInfo(@RequestBody UserDTO userDTO){
        return userService.EditUserInfo(userDTO);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/put_message")
    public ResponseEntity<User> putMessage(@RequestBody LoginDTO loginDTO, HttpSession session) {
        return userService.putMessage(loginDTO,session);
    }

    @PostMapping("/saveToken")
    public ResponseEntity<HttpStatus> saveToken(@RequestBody UserTokenDTO userTokenDTO){
        return userService.saveUserToken(userTokenDTO.getId(),userTokenDTO.getFirebaseToken());
    }

}
