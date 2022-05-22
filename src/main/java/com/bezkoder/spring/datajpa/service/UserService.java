package com.bezkoder.spring.datajpa.service;


import com.bezkoder.spring.datajpa.model.Role;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.model.UserDTO;
import com.bezkoder.spring.datajpa.repository.RoleRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User _user;
    private Optional<User> userOptional;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

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