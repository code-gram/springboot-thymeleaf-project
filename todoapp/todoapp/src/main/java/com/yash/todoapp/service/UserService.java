package com.yash.todoapp.service;

import com.yash.todoapp.dto.UserDto;
import com.yash.todoapp.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
