package com.bezkoder.spring.datajpa.service;


import com.bezkoder.spring.datajpa.dto.LoginDTO;
import com.bezkoder.spring.datajpa.model.Bank_acct;
import com.bezkoder.spring.datajpa.model.Role;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.dto.UserDTO;
import com.bezkoder.spring.datajpa.model.Wallet;
import com.bezkoder.spring.datajpa.repository.BankAcctRepository;
import com.bezkoder.spring.datajpa.repository.RoleRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import com.bezkoder.spring.datajpa.repository.WalletRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User _user;
    private Optional<User> userOptional;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private BankAcctRepository bankAcctRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
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
    public ResponseEntity<User> getUserById( long id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> getUserById( String username) {
        User userData = userRepository.findByUserName(username);

        if (userData!=null) {
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> checkUserLogin(HttpSession session, SessionStatus sessionStatus){
        String sessionUsername=session.getAttribute("username").toString();
        System.out.println(sessionUsername);
        if(session.getAttribute("username") != null){
            User user=userRepository.findByUserName(sessionUsername);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<User> registerUser(UserDTO userDTO) {
        try {
            String username= userDTO.getUserName();
            String name= userDTO.getName();
            String lastname= userDTO.getLastName();
            String password= userDTO.getPassword();
            String email= userDTO.getEmail();
            Boolean active= userDTO.getActive();
            User user=new User(username,email,password,name,lastname,active);
            userRepository.save(user);
            walletRepository.save(new Wallet(new BigDecimal("0"),"Create Account",user));
            bankAcctRepository.save(new Bank_acct(null,null,user));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<User> loginUser(LoginDTO loginDTO, HttpSession session) {
        try {
            String username= loginDTO.getUsername();
            String password= loginDTO.getPassword();
            User userData = userRepository.findByUserName(username);
            String _password=userData.getPassword();
            if (this.loginUser(_password,password)) {
                session.setAttribute("username", username);
                return new ResponseEntity<>(userData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
    public ResponseEntity<String> userLogout(HttpSession session, SessionStatus sessionStatus){
        if(session.getAttribute("username") != null){
            session.removeAttribute("username");
            sessionStatus.setComplete();
            return new ResponseEntity<>("Logout", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Not Logout",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<User> changePassword(LoginDTO loginDTO){
        try{
            String username=loginDTO.getUsername();
            String password=loginDTO.getPassword();
            User userData=this.changePassword(username,password);
            return new ResponseEntity<>(userData, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<User> EditUserInfo(UserDTO userDTO){
        try{
            User _user=this.EditUserProfile(userDTO);
            return new ResponseEntity<>(_user,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<HttpStatus> deleteUser(long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<User> putMessage(LoginDTO loginDTO, HttpSession session) {
        try {

            String username= loginDTO.getUsername();
            String password= loginDTO.getPassword();
            User userData = userRepository.findByUserName(username);
            String _password=userData.getPassword();
            rabbitTemplate.convertAndSend("tpu.queue", userData);
            if (this.loginUser(_password,password)) {
                session.setAttribute("username", username);
                return new ResponseEntity<>(userData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

    }
    //Utils Layer

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }
    public User changePassword(String username,String password){
        User _user=userRepository.findByUserName(username);
        _user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(_user);
    }
    public User EditUserProfile(UserDTO userDTO){
        User _user=userRepository.findByUserName(userDTO.getUserName());
        _user.setName(userDTO.getName());
        _user.setEmail(userDTO.getEmail());
        _user.setLastName(userDTO.getLastName());
        return  userRepository.save(_user);
    }
    public boolean loginUser(String _password,String password){
        try{
            if(bCryptPasswordEncoder.matches(password,_password)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }

    public User findUserById(long userId) {
        userOptional = userRepository.findById(userId);
        return userOptional.get();
    }
}