package com.bctech.activitytracker.dto;


import com.bctech.activitytracker.model.Task;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank (message = "Full name is required")
    private String fullName;


    @NotBlank (message = "Phone number is required")
    @Size(min = 11, max = 11)
    private String phoneNumber;

    @Email(message = "A valid Email is required")
    private String email;

    @ToString.Exclude
    private List<Task> tasks;
}
