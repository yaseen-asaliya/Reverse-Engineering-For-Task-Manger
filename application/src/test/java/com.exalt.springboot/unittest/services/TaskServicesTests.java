package com.exalt.springboot.unittest.services;

import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.domain.exception.NotFoundException;
import com.exalt.springboot.repository.entity.TaskEntity;
import com.exalt.springboot.repository.entity.UserEntity;
import com.exalt.springboot.repository.jpa.ITaskJpaRepository;
import com.exalt.springboot.service.implementation.TaskServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServicesTests {

	@Mock
	private ITaskJpaRepository taskRepository;

	@InjectMocks
	private TaskServiceImplementation service;

	@Test
	void shouldGetAllTasks() {
		List<TaskEntity> expectedTasksList = initializeListOfTasks();

		when(taskRepository.findTasksByUserId(anyInt())).thenReturn(expectedTasksList);
		assertEquals(expectedTasksList,service.getTasks(anyInt()));
	}

	@Test
	void shouldGetAllUserTasksAsPagination() {
		Pageable paging = PageRequest.of(0, 2, Sort.Direction.ASC,"start");
		List<TaskEntity> expectedTasksList = initializeListOfTasks();
		Page<TaskEntity> expectedTasksListWithPagination = new PageImpl<>(expectedTasksList);

		when(taskRepository.findTasksByUserIdWithPagination(anyInt(),any())).thenReturn(expectedTasksListWithPagination);
		assertEquals(expectedTasksListWithPagination,service.getTasks(1,paging));
	}

	@Test
	void shouldAddTask() {
		TaskEntity newTask = initializeTask();

		when(taskRepository.save(any())).thenReturn(newTask);
		assertEquals("Task saved",service.saveObject(any()));
	}

	@Test
	void shouldUpdateTask() {
		TaskEntity updatedTask = initializeTask();
		updatedTask.setDescription("do idk..."); //Do some changes...

		when(taskRepository.save(any())).thenReturn(updatedTask);
		assertEquals("Task saved",service.saveObject(any()));
	}

	@Test
	void shouldDeleteTask() {
		// Here we don't need to mock the "deleteById" cause it's a void method, so we just verify it.

		assertEquals("Task deleted",service.deleteById(anyInt()));
		verify(taskRepository).deleteById(anyInt()); //check that the method was called successfully
	}

	@Test
	void shouldGetTask() {
		TaskEntity expectedTask = initializeTask();

		when(taskRepository.findById(anyInt())).thenReturn(Optional.of(expectedTask));
		assertEquals(expectedTask,service.findById(anyInt()));
	}

	@Test
	void shouldGetWrongTaskId() {
		boolean thrown = false;
		try {
			service.findById(anyInt());
		} catch (NotFoundException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	private TaskEntity initializeTask() {
		return new TaskEntity(1,initializeUser(),"do something...",true,"2022-05-16 08:30:10","2022-05-16 08:50:00");
	}


	private List<TaskEntity> initializeListOfTasks() {
		List<TaskEntity> taskList = new ArrayList<>();
		UserEntity user = initializeUser();
		taskList.add(new TaskEntity(1,user,"do something...",true,"2022-05-16 08:30:10","2022-05-16 08:50:00"));
		taskList.add(new TaskEntity(2,user,"do something...",false,"2022-05-17 08:30:10","2022-05-17 08:50:00"));
		return taskList;
	}

	private User initializeDomainUser() {
		User user = new User(1,"yaseen","123","yaseens@gmail.com","ys",false);
		return user;
	}

	private UserEntity initializeUser() {
		return new UserEntity(1,"yaseen","123","yaseens@gmail.com","ys");
	}
}
