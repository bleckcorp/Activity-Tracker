package com.bctech.activitytracker.service.serviceImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bctech.activitytracker.dto.TaskDto;
import com.bctech.activitytracker.dto.UserDto;
import com.bctech.activitytracker.exceptions.CustomException;
import com.bctech.activitytracker.model.Task;
import com.bctech.activitytracker.model.User;
import com.bctech.activitytracker.repository.TaskRepository;
import com.bctech.activitytracker.repository.UserRepository;

import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskServiceImpl.class, UserDto.class})
@ExtendWith(SpringExtension.class)
class TaskServiceImplTest {
    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @Autowired
    private UserDto userDto;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link TaskServiceImpl#saveTask(TaskDto, Long)}
     */
    @Test
    void testSaveTask() {
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        User user = new User();
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(user));
        Task actualSaveTaskResult = taskServiceImpl.saveTask(new TaskDto(), 123L);
        assertNull(actualSaveTaskResult.getCompletedAt());
        assertSame(user, actualSaveTaskResult.getUser());
        assertNull(actualSaveTaskResult.getUpdatedAt());
        assertNull(actualSaveTaskResult.getCreatedAt());
        assertEquals("PENDING", actualSaveTaskResult.getStatus());
        assertNull(actualSaveTaskResult.getTaskId());
        assertNull(actualSaveTaskResult.getDescription());
        assertNull(actualSaveTaskResult.getTitle());
        verify(taskRepository).save((Task) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#saveTask(TaskDto, Long)}
     */
    @Test
    void testSaveTask2() {
        when(taskRepository.save((Task) any())).thenThrow(new CustomException("An error occurred"));
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        assertThrows(CustomException.class, () -> taskServiceImpl.saveTask(new TaskDto(), 123L));
        verify(taskRepository).save((Task) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#saveTask(TaskDto, Long)}
     */
    @Test
    void testSaveTask3() {
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> taskServiceImpl.saveTask(new TaskDto(), 123L));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#saveTask(TaskDto, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSaveTask4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.bctech.activitytracker.dto.TaskDto.getTitle()" because "taskRequest" is null
        //       at com.bctech.activitytracker.service.serviceImplementation.TaskServiceImpl.saveTask(TaskServiceImpl.java:33)
        //   See https://diff.blue/R013 to resolve this issue.

        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        taskServiceImpl.saveTask(null, 123L);
    }

    /**
     * Method under test: {@link TaskServiceImpl#saveTask(TaskDto, Long)}
     */
    @Test
    void testSaveTask5() {
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        User user = new User();
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(user));
        TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(taskDto.getTitle()).thenReturn("Dr");
        Task actualSaveTaskResult = taskServiceImpl.saveTask(taskDto, 123L);
        assertNull(actualSaveTaskResult.getCompletedAt());
        assertSame(user, actualSaveTaskResult.getUser());
        assertNull(actualSaveTaskResult.getUpdatedAt());
        assertNull(actualSaveTaskResult.getCreatedAt());
        assertEquals("PENDING", actualSaveTaskResult.getStatus());
        assertNull(actualSaveTaskResult.getTaskId());
        assertEquals("The characteristics of someone or something", actualSaveTaskResult.getDescription());
        assertEquals("Dr", actualSaveTaskResult.getTitle());
        verify(taskRepository).save((Task) any());
        verify(userRepository).findById((Long) any());
        verify(taskDto).getDescription();
        verify(taskDto, atLeast(1)).getTitle();
    }

    /**
     * Method under test: {@link TaskServiceImpl#updateTask(TaskDto, UserDto)}
     */
    @Test
    void testUpdateTask() {
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(Optional.of(new Task()));
        TaskDto taskRequest = new TaskDto();
        assertNull(taskServiceImpl.updateTask(taskRequest, userDto));
        verify(taskRepository).save((Task) any());
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#updateTask(TaskDto, UserDto)}
     */
    @Test
    void testUpdateTask2() {
        when(taskRepository.save((Task) any())).thenThrow(new CustomException("An error occurred"));
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(Optional.of(new Task()));
        TaskDto taskRequest = new TaskDto();
        assertThrows(CustomException.class, () -> taskServiceImpl.updateTask(taskRequest, userDto));
        verify(taskRepository).save((Task) any());
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#updateTask(TaskDto, UserDto)}
     */
    @Test
    void testUpdateTask3() {
        Task task = mock(Task.class);
        when(task.getTaskId()).thenReturn(123L);
        doNothing().when(task).setDescription((String) any());
        doNothing().when(task).setStatus((String) any());
        doNothing().when(task).setTitle((String) any());
        doNothing().when(task).setUpdatedAt((LocalDateTime) any());
        Optional<Task> ofResult = Optional.of(task);
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);
        TaskDto taskRequest = new TaskDto();
        assertEquals(123L, taskServiceImpl.updateTask(taskRequest, userDto).longValue());
        verify(taskRepository).save((Task) any());
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
        verify(task).getTaskId();
        verify(task).setDescription((String) any());
        verify(task).setStatus((String) any());
        verify(task).setTitle((String) any());
        verify(task).setUpdatedAt((LocalDateTime) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#updateTask(TaskDto, UserDto)}
     */
    @Test
    void testUpdateTask4() {
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(Optional.empty());
        TaskDto taskRequest = new TaskDto();
        assertThrows(CustomException.class, () -> taskServiceImpl.updateTask(taskRequest, userDto));
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#updateTask(TaskDto, UserDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateTask5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.bctech.activitytracker.dto.TaskDto.getTitle()" because "taskRequest" is null
        //       at com.bctech.activitytracker.service.serviceImplementation.TaskServiceImpl.updateTask(TaskServiceImpl.java:54)
        //   See https://diff.blue/R013 to resolve this issue.

        Task task = mock(Task.class);
        when(task.getTaskId()).thenReturn(123L);
        doNothing().when(task).setDescription((String) any());
        doNothing().when(task).setStatus((String) any());
        doNothing().when(task).setTitle((String) any());
        doNothing().when(task).setUpdatedAt((LocalDateTime) any());
        Optional<Task> ofResult = Optional.of(task);
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);
        taskServiceImpl.updateTask(null, userDto);
    }

    /**
     * Method under test: {@link TaskServiceImpl#updateTask(TaskDto, UserDto)}
     */
    @Test
    void testUpdateTask6() {
        Task task = mock(Task.class);
        when(task.getTaskId()).thenReturn(123L);
        doNothing().when(task).setDescription((String) any());
        doNothing().when(task).setStatus((String) any());
        doNothing().when(task).setTitle((String) any());
        doNothing().when(task).setUpdatedAt((LocalDateTime) any());
        Optional<Task> ofResult = Optional.of(task);
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);
        TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(taskDto.getTaskId()).thenReturn(123L);
        when(taskDto.getStatus()).thenReturn("Status");
        when(taskDto.getTitle()).thenReturn("Dr");
        assertEquals(123L, taskServiceImpl.updateTask(taskDto, userDto).longValue());
        verify(taskRepository).save((Task) any());
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
        verify(task).getTaskId();
        verify(task).setDescription((String) any());
        verify(task).setStatus((String) any());
        verify(task).setTitle((String) any());
        verify(task).setUpdatedAt((LocalDateTime) any());
        verify(taskDto).getTaskId();
        verify(taskDto).getDescription();
        verify(taskDto, atLeast(1)).getStatus();
        verify(taskDto, atLeast(1)).getTitle();
    }

    /**
     * Method under test: {@link TaskServiceImpl#updateTask(TaskDto, UserDto)}
     */
    @Test
    void testUpdateTask7() {
        Task task = mock(Task.class);
        when(task.getTaskId()).thenReturn(123L);
        doNothing().when(task).setDescription((String) any());
        doNothing().when(task).setStatus((String) any());
        doNothing().when(task).setTitle((String) any());
        doNothing().when(task).setUpdatedAt((LocalDateTime) any());
        Optional<Task> ofResult = Optional.of(task);
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);
        TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getDescription()).thenThrow(new CustomException("An error occurred"));
        when(taskDto.getTaskId()).thenReturn(123L);
        when(taskDto.getStatus()).thenReturn("Status");
        when(taskDto.getTitle()).thenReturn("Dr");
        assertThrows(CustomException.class, () -> taskServiceImpl.updateTask(taskDto, userDto));
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
        verify(task).setTitle((String) any());
        verify(task).setUpdatedAt((LocalDateTime) any());
        verify(taskDto).getTaskId();
        verify(taskDto).getDescription();
        verify(taskDto).getStatus();
        verify(taskDto, atLeast(1)).getTitle();
    }

    /**
     * Method under test: {@link TaskServiceImpl#updateTask(TaskDto, UserDto)}
     */
    @Test
    void testUpdateTask8() {
        Task task = mock(Task.class);
        doNothing().when(task).setCompletedAt((LocalDateTime) any());
        when(task.getTaskId()).thenReturn(123L);
        doNothing().when(task).setDescription((String) any());
        doNothing().when(task).setStatus((String) any());
        doNothing().when(task).setTitle((String) any());
        doNothing().when(task).setUpdatedAt((LocalDateTime) any());
        Optional<Task> ofResult = Optional.of(task);
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);
        TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(taskDto.getTaskId()).thenReturn(123L);
        when(taskDto.getStatus()).thenReturn("COMPLETED");
        when(taskDto.getTitle()).thenReturn("Dr");
        assertEquals(123L, taskServiceImpl.updateTask(taskDto, userDto).longValue());
        verify(taskRepository).save((Task) any());
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
        verify(task).getTaskId();
        verify(task).setCompletedAt((LocalDateTime) any());
        verify(task).setDescription((String) any());
        verify(task).setStatus((String) any());
        verify(task).setTitle((String) any());
        verify(task).setUpdatedAt((LocalDateTime) any());
        verify(taskDto).getTaskId();
        verify(taskDto).getDescription();
        verify(taskDto, atLeast(1)).getStatus();
        verify(taskDto, atLeast(1)).getTitle();
    }

    /**
     * Method under test: {@link TaskServiceImpl#updateTask(TaskDto, UserDto)}
     */
    @Test
    void testUpdateTask9() {
        Task task = mock(Task.class);
        doThrow(new CustomException("An error occurred")).when(task).setCompletedAt((LocalDateTime) any());
        when(task.getTaskId()).thenReturn(123L);
        doNothing().when(task).setDescription((String) any());
        doNothing().when(task).setStatus((String) any());
        doNothing().when(task).setTitle((String) any());
        doNothing().when(task).setUpdatedAt((LocalDateTime) any());
        Optional<Task> ofResult = Optional.of(task);
        when(taskRepository.save((Task) any())).thenReturn(new Task());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(ofResult);
        TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(taskDto.getTaskId()).thenReturn(123L);
        when(taskDto.getStatus()).thenReturn("COMPLETED");
        when(taskDto.getTitle()).thenReturn("Dr");
        assertThrows(CustomException.class, () -> taskServiceImpl.updateTask(taskDto, userDto));
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
        verify(task).setCompletedAt((LocalDateTime) any());
        verify(taskDto).getTaskId();
        verify(taskDto).getStatus();
        verify(taskDto).getTitle();
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUser(UserDto)}
     */
    @Test
    void testGetAllTasksOfUser() {
        when(taskRepository.findAllByUserId((Long) any())).thenReturn(new ArrayList<>());
        assertThrows(CustomException.class, () -> taskServiceImpl.getAllTasksOfUser(userDto));
        verify(taskRepository).findAllByUserId((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUser(UserDto)}
     */
    @Test
    void testGetAllTasksOfUser2() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Task());
        when(taskRepository.findAllByUserId((Long) any())).thenReturn(taskList);
        assertEquals(1, taskServiceImpl.getAllTasksOfUser(userDto).size());
        verify(taskRepository).findAllByUserId((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUser(UserDto)}
     */
    @Test
    void testGetAllTasksOfUser3() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Task());
        taskList.add(new Task());
        when(taskRepository.findAllByUserId((Long) any())).thenReturn(taskList);
        assertEquals(2, taskServiceImpl.getAllTasksOfUser(userDto).size());
        verify(taskRepository).findAllByUserId((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUser(UserDto)}
     */
    @Test
    void testGetAllTasksOfUser4() {
        when(taskRepository.findAllByUserId((Long) any())).thenThrow(new CustomException("An error occurred"));
        assertThrows(CustomException.class, () -> taskServiceImpl.getAllTasksOfUser(userDto));
        verify(taskRepository).findAllByUserId((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUser(UserDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllTasksOfUser5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.bctech.activitytracker.model.Task.getTaskId()" because "task" is null
        //       at com.bctech.activitytracker.service.serviceImplementation.TaskServiceImpl.lambda$convertListOfTasksToDtos$4(TaskServiceImpl.java:123)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at com.bctech.activitytracker.service.serviceImplementation.TaskServiceImpl.convertListOfTasksToDtos(TaskServiceImpl.java:130)
        //       at com.bctech.activitytracker.service.serviceImplementation.TaskServiceImpl.getAllTasksOfUser(TaskServiceImpl.java:79)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(null);
        when(taskRepository.findAllByUserId((Long) any())).thenReturn(taskList);
        taskServiceImpl.getAllTasksOfUser(userDto);
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUser(UserDto)}
     */
    @Test
    void testGetAllTasksOfUser6() {
        ArrayList<Task> taskList = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime updatedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime completedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        taskList.add(new Task(123L, "Dr", "The characteristics of someone or something",
                "service:: about to get all tasks of user :: {}", createdAt, updatedAt, completedAt, new User()));
        when(taskRepository.findAllByUserId((Long) any())).thenReturn(taskList);
        assertEquals(1, taskServiceImpl.getAllTasksOfUser(userDto).size());
        verify(taskRepository).findAllByUserId((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUser(UserDto)}
     */
    @Test
    void testGetAllTasksOfUser7() {
        Task task = mock(Task.class);
        when(task.getTaskId()).thenReturn(123L);
        when(task.getDescription()).thenReturn("The characteristics of someone or something");
        when(task.getStatus()).thenReturn("Status");
        when(task.getTitle()).thenReturn("Dr");
        when(task.getCompletedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(task.getCreatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(task.getUpdatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(task);
        when(taskRepository.findAllByUserId((Long) any())).thenReturn(taskList);
        assertEquals(1, taskServiceImpl.getAllTasksOfUser(userDto).size());
        verify(taskRepository).findAllByUserId((Long) any());
        verify(task).getTaskId();
        verify(task).getDescription();
        verify(task).getStatus();
        verify(task).getTitle();
        verify(task, atLeast(1)).getCompletedAt();
        verify(task, atLeast(1)).getCreatedAt();
        verify(task, atLeast(1)).getUpdatedAt();
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUserAccordingToCategory(UserDto, String)}
     */
    @Test
    void testGetAllTasksOfUserAccordingToCategory() {
        when(taskRepository.findAllByUserIdAndStatus((Long) any(), (String) any())).thenReturn(new ArrayList<>());
        assertThrows(CustomException.class,
                () -> taskServiceImpl.getAllTasksOfUserAccordingToCategory(userDto, "Status"));
        verify(taskRepository).findAllByUserIdAndStatus((Long) any(), (String) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUserAccordingToCategory(UserDto, String)}
     */
    @Test
    void testGetAllTasksOfUserAccordingToCategory2() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Task());
        when(taskRepository.findAllByUserIdAndStatus((Long) any(), (String) any())).thenReturn(taskList);
        assertEquals(1, taskServiceImpl.getAllTasksOfUserAccordingToCategory(userDto, "Status").size());
        verify(taskRepository).findAllByUserIdAndStatus((Long) any(), (String) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUserAccordingToCategory(UserDto, String)}
     */
    @Test
    void testGetAllTasksOfUserAccordingToCategory3() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new Task());
        taskList.add(new Task());
        when(taskRepository.findAllByUserIdAndStatus((Long) any(), (String) any())).thenReturn(taskList);
        assertEquals(2, taskServiceImpl.getAllTasksOfUserAccordingToCategory(userDto, "Status").size());
        verify(taskRepository).findAllByUserIdAndStatus((Long) any(), (String) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUserAccordingToCategory(UserDto, String)}
     */
    @Test
    void testGetAllTasksOfUserAccordingToCategory4() {
        when(taskRepository.findAllByUserIdAndStatus((Long) any(), (String) any()))
                .thenThrow(new CustomException("An error occurred"));
        assertThrows(CustomException.class,
                () -> taskServiceImpl.getAllTasksOfUserAccordingToCategory(userDto, "Status"));
        verify(taskRepository).findAllByUserIdAndStatus((Long) any(), (String) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUserAccordingToCategory(UserDto, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllTasksOfUserAccordingToCategory5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.bctech.activitytracker.model.Task.getTaskId()" because "task" is null
        //       at com.bctech.activitytracker.service.serviceImplementation.TaskServiceImpl.lambda$convertListOfTasksToDtos$4(TaskServiceImpl.java:123)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at com.bctech.activitytracker.service.serviceImplementation.TaskServiceImpl.convertListOfTasksToDtos(TaskServiceImpl.java:130)
        //       at com.bctech.activitytracker.service.serviceImplementation.TaskServiceImpl.getAllTasksOfUserAccordingToCategory(TaskServiceImpl.java:93)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(null);
        when(taskRepository.findAllByUserIdAndStatus((Long) any(), (String) any())).thenReturn(taskList);
        taskServiceImpl.getAllTasksOfUserAccordingToCategory(userDto, "Status");
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUserAccordingToCategory(UserDto, String)}
     */
    @Test
    void testGetAllTasksOfUserAccordingToCategory6() {
        ArrayList<Task> taskList = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime updatedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime completedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        taskList.add(new Task(123L, "Dr", "The characteristics of someone or something",
                "service:: about to get all tasks by status for user :: {}", createdAt, updatedAt, completedAt, new User()));
        when(taskRepository.findAllByUserIdAndStatus((Long) any(), (String) any())).thenReturn(taskList);
        assertEquals(1, taskServiceImpl.getAllTasksOfUserAccordingToCategory(userDto, "Status").size());
        verify(taskRepository).findAllByUserIdAndStatus((Long) any(), (String) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getAllTasksOfUserAccordingToCategory(UserDto, String)}
     */
    @Test
    void testGetAllTasksOfUserAccordingToCategory7() {
        Task task = mock(Task.class);
        when(task.getTaskId()).thenReturn(123L);
        when(task.getDescription()).thenReturn("The characteristics of someone or something");
        when(task.getStatus()).thenReturn("Status");
        when(task.getTitle()).thenReturn("Dr");
        when(task.getCompletedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(task.getCreatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(task.getUpdatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(task);
        when(taskRepository.findAllByUserIdAndStatus((Long) any(), (String) any())).thenReturn(taskList);
        assertEquals(1, taskServiceImpl.getAllTasksOfUserAccordingToCategory(userDto, "Status").size());
        verify(taskRepository).findAllByUserIdAndStatus((Long) any(), (String) any());
        verify(task).getTaskId();
        verify(task).getDescription();
        verify(task).getStatus();
        verify(task).getTitle();
        verify(task, atLeast(1)).getCompletedAt();
        verify(task, atLeast(1)).getCreatedAt();
        verify(task, atLeast(1)).getUpdatedAt();
    }

    /**
     * Method under test: {@link TaskServiceImpl#deleteTask(Long, UserDto)}
     */
    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).delete((Task) any());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(Optional.of(new Task()));
        assertTrue(taskServiceImpl.deleteTask(123L, userDto));
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
        verify(taskRepository).delete((Task) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#deleteTask(Long, UserDto)}
     */
    @Test
    void testDeleteTask2() {
        doThrow(new CustomException("An error occurred")).when(taskRepository).delete((Task) any());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(Optional.of(new Task()));
        assertThrows(CustomException.class, () -> taskServiceImpl.deleteTask(123L, userDto));
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
        verify(taskRepository).delete((Task) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#deleteTask(Long, UserDto)}
     */
    @Test
    void testDeleteTask3() {
        doNothing().when(taskRepository).delete((Task) any());
        when(taskRepository.findByTaskIdAndUserId((Long) any(), (Long) any())).thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> taskServiceImpl.deleteTask(123L, userDto));
        verify(taskRepository).findByTaskIdAndUserId((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getTaskById(Long)}
     */
    @Test
    void testGetTaskById() {
        when(taskRepository.findById((Long) any())).thenReturn(Optional.of(new Task()));
        TaskDto actualTaskById = taskServiceImpl.getTaskById(123L);
        assertNull(actualTaskById.getCompletedAt());
        assertNull(actualTaskById.getUser());
        assertNull(actualTaskById.getUpdatedAt());
        assertNull(actualTaskById.getTitle());
        assertNull(actualTaskById.getTaskId());
        assertEquals("PENDING", actualTaskById.getStatus());
        assertNull(actualTaskById.getDescription());
        assertNull(actualTaskById.getCreatedAt());
        verify(taskRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getTaskById(Long)}
     */
    @Test
    void testGetTaskById2() {
        Task task = mock(Task.class);
        when(task.getTaskId()).thenReturn(123L);
        when(task.getDescription()).thenReturn("The characteristics of someone or something");
        when(task.getStatus()).thenReturn("Status");
        when(task.getTitle()).thenReturn("Dr");
        Optional<Task> ofResult = Optional.of(task);
        when(taskRepository.findById((Long) any())).thenReturn(ofResult);
        TaskDto actualTaskById = taskServiceImpl.getTaskById(123L);
        assertNull(actualTaskById.getCompletedAt());
        assertNull(actualTaskById.getUser());
        assertNull(actualTaskById.getUpdatedAt());
        assertEquals("Dr", actualTaskById.getTitle());
        assertEquals(123L, actualTaskById.getTaskId().longValue());
        assertEquals("Status", actualTaskById.getStatus());
        assertEquals("The characteristics of someone or something", actualTaskById.getDescription());
        assertNull(actualTaskById.getCreatedAt());
        verify(taskRepository).findById((Long) any());
        verify(task).getTaskId();
        verify(task).getDescription();
        verify(task).getStatus();
        verify(task).getTitle();
    }

    /**
     * Method under test: {@link TaskServiceImpl#getTaskById(Long)}
     */
    @Test
    void testGetTaskById3() {
        when(taskRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> taskServiceImpl.getTaskById(123L));
        verify(taskRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link TaskServiceImpl#getTaskById(Long)}
     */
    @Test
    void testGetTaskById4() {
        Task task = mock(Task.class);
        when(task.getTaskId()).thenThrow(new CustomException("An error occurred"));
        when(task.getDescription()).thenThrow(new CustomException("An error occurred"));
        when(task.getStatus()).thenThrow(new CustomException("An error occurred"));
        when(task.getTitle()).thenThrow(new CustomException("An error occurred"));
        Optional<Task> ofResult = Optional.of(task);
        when(taskRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(CustomException.class, () -> taskServiceImpl.getTaskById(123L));
        verify(taskRepository).findById((Long) any());
        verify(task).getTaskId();
    }
}

