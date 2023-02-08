package com.bctech.activitytracker.service.serviceImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bctech.activitytracker.dto.UserDto;
import com.bctech.activitytracker.dto.UserRequestDTO;
import com.bctech.activitytracker.exceptions.CustomException;
import com.bctech.activitytracker.model.User;
import com.bctech.activitytracker.repository.UserRepository;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserRequestDTO)}
     */
    @Test
    void testCreateUser() {
        when(userRepository.save(any())).thenReturn(new User());
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(new User()));
        assertThrows(CustomException.class, () -> userServiceImpl
                .createUser(new UserRequestDTO("Dr Jane Doe", "4105551212", "iloveyou", "jane.doe@example.org")));
        verify(userRepository).findUserByEmail(any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserRequestDTO)}
     */
    @Test
    void testCreateUser2() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.empty());
        UserDto actualCreateUserResult = userServiceImpl
                .createUser(new UserRequestDTO("Dr Jane Doe", "4105551212", "iloveyou", "jane.doe@example.org"));
        assertEquals("jane.doe@example.org", actualCreateUserResult.getEmail());
        assertNull(actualCreateUserResult.getTasks());
        assertEquals("4105551212", actualCreateUserResult.getPhoneNumber());
        assertNull(actualCreateUserResult.getId());
        assertEquals("Dr Jane Doe", actualCreateUserResult.getFullName());
        verify(userRepository).save((User) any());
        verify(userRepository).findUserByEmail(any());
    }


    /**
     * Method under test: {@link UserServiceImpl#createUser(UserRequestDTO)}
     */
    @Test
    void testCreateUser4() {
        when(userRepository.save(any())).thenThrow(new CustomException("An error occurred"));
        when(userRepository.findUserByEmail(any())).thenThrow(new CustomException("An error occurred"));
        assertThrows(CustomException.class, () -> userServiceImpl
                .createUser(new UserRequestDTO("Dr Jane Doe", "4105551212", "iloveyou", "jane.doe@example.org")));
        verify(userRepository).findUserByEmail(any());
    }
}

