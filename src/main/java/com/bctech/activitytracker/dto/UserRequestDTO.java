package com.bctech.activitytracker.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

/*
  This class is used to represent the request body of the user registration endpoint
 */
public class UserRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;


    @NotBlank (message = "Phone number is required")
    @Size(min = 11, max = 11)
    private String phoneNumber;

    @NotBlank(message = "A password is required")
    private String password;

    @Email(message = "A valid Email is required")
    private String email;
}
