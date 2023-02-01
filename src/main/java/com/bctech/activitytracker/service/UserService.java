package com.bctech.activitytracker.service;


import com.bctech.activitytracker.dto.UserDto;
import com.bctech.activitytracker.dto.UserRequestDTO;

public interface UserService {
//    method signature for saving user data
 UserDto createUser(UserRequestDTO request);

//    method signature for validating user data
    UserDto validateUser(String name, String password);

}
