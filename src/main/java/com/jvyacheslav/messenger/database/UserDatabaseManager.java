package com.jvyacheslav.messenger.database;

import com.jvyacheslav.messenger.dto.User;
import com.jvyacheslav.messenger.interfaces.database_repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//methods for working with the database users
@Service
public class UserDatabaseManager {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findByUsernameAndPass(String username, String pass){
        return userRepository.findByUsernameAndPass(username, pass);
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User findByUsernameAndEmail(String username, String email){
        return userRepository.findByUsernameAndEmail(username, email);
    }
}
