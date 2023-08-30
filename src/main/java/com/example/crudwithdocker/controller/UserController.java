package com.example.crudwithdocker.controller;

import com.example.crudwithdocker.model.User;
import com.example.crudwithdocker.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping()
    public List<User> ListAll(){
        return userService.findAllUser();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public User update(@PathVariable Long id , @RequestBody User user){
        return userService.updateUser(id,user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
         userService.deleteUser(id);
    }

    @GetMapping("/find/{id}")
    public User findId(@PathVariable Long id ){
        System.out.println("User" + id);
        return userService.findById(id);

    }

}
