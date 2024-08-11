package com.yash.todoapp.service.impl;

import com.yash.todoapp.dto.UserDto;
import com.yash.todoapp.entity.Role;
import com.yash.todoapp.entity.User;
import com.yash.todoapp.repository.RoleRepository;
import com.yash.todoapp.repository.UserRepository;
import com.yash.todoapp.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName()+" "+userDto.getLastName());
        user.setEmail(userDto.getEmail());
        //encript password using spring security

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        user.setPassword(userDto.getPassword());
        Role role =  roleRepository.findByName("ROLE_USER");
        if(role==null){
            role =  checkRoleExists();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users =  userRepository.findAll();
        return users.stream().map((user)->mapToUserDto(user)).collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto =  new UserDto();
        String [] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }
    private Role checkRoleExists(){
        Role role =  new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);

    }
}
