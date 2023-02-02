package com.bctech.activitytracker.service.serviceImplementation;


import com.bctech.activitytracker.dto.UserDto;
import com.bctech.activitytracker.dto.UserRequestDTO;
import com.bctech.activitytracker.exceptions.CustomException;
import com.bctech.activitytracker.model.User;
import com.bctech.activitytracker.repository.UserRepository;
import com.bctech.activitytracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Service class for implementing business logic on the users data
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    //    implementation of validating users details
    @Override
    public UserDto validateUser(String email, String password) throws CustomException{
        log.info("service:: about to validate user login :: {}", email);
        Optional<User> isUserPresent = userRepository.findUserByEmailAndPassword(email,password);

        if (isUserPresent.isPresent()) {
            User user = isUserPresent.get();
            return UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
        }
        else {
            throw new CustomException("User credentials not correct", HttpStatus.CONFLICT);
        }

    }

    @Override
    public UserDto createUser(UserRequestDTO request) {
        log.info("service:: about to save user with email :: {}", request.getEmail());
        userRepository.findUserByEmail(request.getEmail()).ifPresent(user -> {
            throw new CustomException(String.format("User with email address %s already exist", request.getEmail()), HttpStatus.CONFLICT);
        });

        // IF no exception is thrown, then we can save the user
        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getPhoneNumber())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .build();

        userRepository.save(user);

        return UserDto.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .build();

    }
}
