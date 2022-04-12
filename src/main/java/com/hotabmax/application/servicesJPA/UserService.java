package com.hotabmax.application.servicesJPA;

import com.hotabmax.application.models.User;
import com.hotabmax.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void create(User user){
        userRepository.save(user);
    }

    public List<User> findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public void deleteByLogin(String login){
        userRepository.deleteByLogin(login);
    }
}
