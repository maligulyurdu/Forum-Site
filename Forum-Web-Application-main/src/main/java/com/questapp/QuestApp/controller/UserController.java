package com.questapp.QuestApp.controller;

import com.questapp.QuestApp.model.User;
import com.questapp.QuestApp.repository.UserRepository;
import com.questapp.QuestApp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController (UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createOneUser(@RequestBody User newUser){
        return userService.saveUser(newUser);
    }

    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId){
        // custom exception
        return userService.getOneUser(userId);
    }

    @PutMapping("/{userId}") // We need to get the ID and new information of the user we want to change.
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser){
        return userService.updateOneUser(userId,newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId){
        userService.deleteOneUser(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getActivity(@PathVariable Long userId){
        return userService.getUserActivity(userId);
    }
}

