package com.example.crudwithdocker.service.impl;

import com.example.crudwithdocker.model.User;
import com.example.crudwithdocker.repository.UserRepository;
import com.example.crudwithdocker.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List <User> findAllUser() {
       return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {

        if (!userRepository.existsById(id)) {
            System.out.println("User not Exist");
        }

       user.setId(id);
       userRepository.save(user);
       return user;
    }

    @Override
    public Void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            System.out.println("User not Exist");
        }
        userRepository.deleteById(id);

        return null;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }
}
