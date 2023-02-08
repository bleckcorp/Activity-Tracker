package com.bctech.activitytracker.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String title;

    private String description;

    private String status = "PENDING";

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime  updatedAt;

    private LocalDateTime  completedAt;


    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return taskId != null && Objects.equals(taskId, task.taskId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
