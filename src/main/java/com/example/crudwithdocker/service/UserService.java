package com.example.crudwithdocker.service;

import com.example.crudwithdocker.model.User;
import org.flywaydb.core.api.output.ValidateOutput;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> findAllUser();

    User updateUser(Long id , User user);

    Void deleteUser(Long id);

    User findById(Long id);
}
