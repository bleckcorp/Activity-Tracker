package com.bctech.activitytracker.dto;
import com.bctech.activitytracker.model.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @NotBlank (message = "Task description is required")
    private Long taskId;

    @NotBlank (message = "Task title is required")
    private String title;

    @NotBlank (message = "Task descrition is required")
    private String description;

    private String status = "PENDING";


    private String createdAt;


    private String  updatedAt;


    private String  completedAt;

    @ManyToOne
    private User user;

}
