package com.exalt.springboot.unittest.services;

import com.exalt.springboot.domain.aggregate.Task;
import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.domain.service.ITaskService;
import com.exalt.springboot.repository.adapter.TaskRepositoryAdapter;
import com.exalt.springboot.repository.entity.TaskEntity;
import com.exalt.springboot.repository.entity.UserEntity;
import com.exalt.springboot.repository.jpa.ITaskJpaRepository;
import com.exalt.springboot.service.implementation.TaskServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.domain.*;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootConfiguration
@ContextConfiguration
class TaskServicesTests {

	private ITaskJpaRepository iTaskJpaRepository = Mockito.mock(ITaskJpaRepository.class);

	private ModelMapper modelMapper = mock(ModelMapper.class);

	private TaskRepositoryAdapter taskRepositoryAdapter = Mockito.spy(new TaskRepositoryAdapter(iTaskJpaRepository,modelMapper));

	private ITaskService iTaskService = Mockito.spy(new TaskServiceImplementation(taskRepositoryAdapter));


	@Test
	void shouldGetAllTasks() {
		List<Task> expectedTasksList = initializeListOfTasks();

		when(iTaskJpaRepository.findTasksByUserId(anyInt())).thenReturn(initializeListOfTasksEntity());
		assertEquals(expectedTasksList.toString(),iTaskService.getTasks(anyInt()).toString());
	}

	@Test
	void shouldGetAllUserTasksAsPagination() {
		Pageable paging = PageRequest.of(0, 2, Sort.Direction.ASC,"start");
		List<TaskEntity> tempListOfTasks = initializeListOfTasksEntity();
		Page<TaskEntity> expectedTasksListWithPagination = new PageImpl<>(tempListOfTasks);

		Page<Task> expected = new PageImpl<>(initializeListOfTasks());

		when(iTaskJpaRepository.findTasksByUserIdWithPagination(anyInt(),any())).thenReturn(expectedTasksListWithPagination);
		assertEquals(expected.toString(),iTaskService.getTasks(1,paging).toString());
	}

	@Test
	void shouldAddTask() {
		TaskEntity newTask = initializeTaskEntity();

		when(iTaskJpaRepository.save(any())).thenReturn(newTask);
		assertEquals("Task saved",iTaskService.saveObject(any()));
	}

	@Test
	void shouldUpdateTask() {
		TaskEntity updatedTask = initializeTaskEntity();
		updatedTask.setDescription("do idk..."); //Do some changes...

		when(iTaskJpaRepository.save(any())).thenReturn(updatedTask);
		assertEquals("Task saved",iTaskService.saveObject(any()));
	}

	@Test
	void shouldDeleteTask() {
		// Here we don't need to mock the "deleteById" cause it's a void method, so we just verify it.

		assertEquals("Task deleted",iTaskService.deleteById(anyInt()));
		verify(iTaskJpaRepository).deleteById(anyInt()); //check that the method was called successfully
	}

	@Test
	void shouldGetTask() {
		TaskEntity expectedTask = initializeTaskEntity();

		when(iTaskJpaRepository.findById(anyInt())).thenReturn(Optional.of(expectedTask));
		assertEquals(initializeTask().toString(),iTaskService.findById(anyInt()).toString());
	}

	@Test
	void shouldGetWrongTaskId() {
		boolean thrown = false;
		try {
			iTaskService.findById(anyInt());
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	private List<Task> initializeListOfTasks() {
		List<Task> taskList = new ArrayList<>();
		User user = initializeUser();
		taskList.add(new Task(1,user,"do something...",true,"2022-05-16 08:30:10","2022-05-16 08:50:00"));
		taskList.add(new Task(2,user,"do something...",false,"2022-05-17 08:30:10","2022-05-17 08:50:00"));
		return taskList;
	}

	private List<TaskEntity> initializeListOfTasksEntity() {
		List<TaskEntity> taskList = new ArrayList<>();
		UserEntity user = initializeUserEntity();
		taskList.add(new TaskEntity(1,user,"do something...",true,"2022-05-16 08:30:10","2022-05-16 08:50:00"));
		taskList.add(new TaskEntity(2,user,"do something...",false,"2022-05-17 08:30:10","2022-05-17 08:50:00"));
		return taskList;
	}

	private User initializeUser() {
		User user = new User(1,"yaseen","123","yaseens@gmail.com","ys",false);
		return user;
	}

	private UserEntity initializeUserEntity() {
		UserEntity user = new UserEntity(1,"yaseen","123","yaseens@gmail.com","ys");
		user.setSignout(false);
		return user;
	}

	private TaskEntity initializeTaskEntity() {
		return new TaskEntity(1,initializeUserEntity(),"do something...",true,"2022-05-16 08:30:10","2022-05-16 08:50:00");
	}

	private Task initializeTask() {
		return new Task(1,initializeUser(),"do something...",true,"2022-05-16 08:30:10","2022-05-16 08:50:00");
	}
}
