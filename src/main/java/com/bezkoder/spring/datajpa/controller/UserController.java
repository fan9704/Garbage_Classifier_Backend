package com.bezkoder.spring.datajpa.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bezkoder.spring.datajpa.model.LoginDTO;
import com.bezkoder.spring.datajpa.model.UserDTO;
import com.bezkoder.spring.datajpa.model.*;
import com.bezkoder.spring.datajpa.repository.BankAcctRepository;
import com.bezkoder.spring.datajpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bezkoder.spring.datajpa.repository.WalletRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    BankAcctRepository bankAcctRepository;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> users = new ArrayList<User>();
            userRepository.findAll().forEach(users::add);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userinfo/{username}")
    public ResponseEntity<User> getUserById(@PathVariable("username") String username) {
        User userData = userRepository.findByUserName(username);

        if (userData!=null) {
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
        try {
            String username= userDTO.getUserName();
            String name= userDTO.getName();
            String lastname= userDTO.getLastName();
            String password= userDTO.getPassword();
            String email= userDTO.getEmail();
            Boolean active= userDTO.getActive();
            User user=new User(username,email,password,name,lastname,active);//,new Set<Role>()
            userService.saveUser(user);
           walletRepository.save(new Wallet(new BigDecimal("0"),"Create Account",user));
           bankAcctRepository.save(new Bank_acct(null,null,user));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            String username= loginDTO.getUsername();
            String password= loginDTO.getPassword();
            User userData = userRepository.findByUserName(username);
            String _password=userData.getPassword();
            if (userService.loginUser(_password,password)) {
                return new ResponseEntity<>(userData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
    @PutMapping("/changePassword")
    public ResponseEntity<User> changePassword(@RequestBody LoginDTO loginDTO){
        try{
            String username=loginDTO.getUsername();
            String password=loginDTO.getPassword();
            User userData=userService.changePassword(username,password);
            return new ResponseEntity<>(userData, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/EditUserInfo")
    public ResponseEntity<User> EditUserIngo(@RequestBody UserDTO userDTO){
        try{
            User _user=userService.EditUserProfile(userDTO);
            return new ResponseEntity<>(_user,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
