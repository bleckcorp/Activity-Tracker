package com.bctech.activitytracker.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Full name is required")
    private String fullName;

    @NotEmpty (message = "Phone number is required")
    @Size(min = 11, max = 11)
    private String phoneNumber;

    @NotEmpty(message = "A password is required")
    private String password;

    @NotEmpty(message = "A valid Email is required")
    @Email(message = "A valid Email is required")
    private String email;
}
