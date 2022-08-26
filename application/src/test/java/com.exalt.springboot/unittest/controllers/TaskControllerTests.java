package com.exalt.springboot.unittest.controllers;

import com.exalt.springboot.domain.aggregate.Task;
import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.repository.entity.TaskEntity;
import com.exalt.springboot.repository.entity.UserEntity;
import com.exalt.springboot.repository.jpa.ITaskJpaRepository;
import com.exalt.springboot.repository.jpa.IUserJpaRepository;
import com.exalt.springboot.security.jwt.AuthTokenFilter;
import com.exalt.springboot.service.implementation.TaskServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.exalt.springboot.unittest.controllers.SignupTests.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskServiceImplementation taskService;

    @Mock
    private ITaskJpaRepository taskRepository;

    @MockBean
    private AuthTokenFilter authTokenFilter;

    @MockBean
    private IUserJpaRepository userRepository;


    private void commonMocks() {
        when(authTokenFilter.getUserId()).thenReturn(1);
        when(taskService.getTasks(anyInt())).thenReturn(initializeListOfTasks());
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(initializeUser()));
        when(taskService.findById(anyInt())).thenReturn(initializeDomainTask());
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldGetAllTasksAscending() throws Exception {
        MultiValueMap<String, Object> paraMap = getParaMap("ascending");
        URI uri = getUrl("ascending");
        Page<Task> expectedTasksListWithPagination = new PageImpl<>(initializeListOfTasks());

        commonMocks();
        when(taskService.getTasks(anyInt(),any())).thenReturn(expectedTasksListWithPagination);

        MvcResult mvcResult = mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paraMap)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(asJsonString(expectedTasksListWithPagination),mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldGetAllTasksDescending() throws Exception {
        MultiValueMap<String, Object> paraMap = getParaMap("descending");
        URI uri = getUrl("descending");
        Page<Task> expectedTasksListWithPagination = new PageImpl<>(initializeListOfTasks());

        commonMocks();
        when(taskService.getTasks(anyInt(),any())).thenReturn(expectedTasksListWithPagination);

        MvcResult mvcResult = mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paraMap)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(asJsonString(expectedTasksListWithPagination),mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldNotGetAllTasksWrongDirection() throws Exception {
        MultiValueMap<String, Object> paraMap = getParaMap("as");
        URI uri = getUrl("as");
        Page<Task> expectedTasksListWithPagination = new PageImpl<>(initializeListOfTasks());

        commonMocks();
        when(taskService.getTasks(anyInt(),any())).thenReturn(expectedTasksListWithPagination);

        MvcResult mvcResult = mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paraMap)))
                .andReturn();

        assertEquals(true,mvcResult.getResponse().getContentAsString().contains("Wrong direction passed."));
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldGetNoTasksAvailable() throws Exception {
        MultiValueMap<String, Object> paraMap = getParaMap("ascending");
        URI uri = getUrl("ascending");

        commonMocks();
        when(taskService.getTasks(anyInt())).thenReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paraMap)))
                .andDo(print())
                .andReturn();

        assertEquals(true,mvcResult.getResponse().getContentAsString().contains("No tasks available"));
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldAddTask() throws Exception {
        TaskEntity newTask = initializeTask();
        String expectedBody = newTask + " added successfully.";

        commonMocks();

        MvcResult mvcResult = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTask)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expectedBody,mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldNotAddTaskBecauseOfConflictWhenOtherTasks() throws Exception {
        TaskEntity newTask = initializeTask();
        newTask.setId(3);

        commonMocks();

        MvcResult mvcResult = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTask)))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(true,mvcResult.getResponse().getContentAsString().contains("Conflict between tasks times."));

    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldNotAddTaskUser() throws Exception {
        TaskEntity newTask = initializeTask();

        when(userRepository.findById(anyInt())).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTask)))
                .andReturn();

        assertEquals(true,mvcResult.getResponse().getContentAsString().contains("User not found"));
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldSayUserSignout() throws Exception {
        TaskEntity newTask = initializeTask();
        UserEntity tempUser = initializeUser();
        tempUser.setSignout(true);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(tempUser));

        MvcResult mvcResult = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTask)))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(true,mvcResult.getResponse().getContentAsString().contains("You're unauthorized"));
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldUpdateTask() throws Exception {
        TaskEntity newTask = initializeTask();
        newTask.setCompleted(false); // Do some changes...
        String expectedBody = newTask + " updated successfully.";

        commonMocks();

        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(newTask));
        when(taskService.saveObject(any())).thenReturn(String.valueOf(newTask));

        MvcResult mvcResult = mockMvc.perform(put("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTask)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expectedBody,mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldNotUpdateTaskForAnotherUser() throws Exception {
        TaskEntity newTask = initializeTask();
        newTask.setCompleted(false); // Do some changes...

        when(taskRepository.findById(anyInt())).thenReturn(null);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(initializeUser()));

        MvcResult mvcResult = mockMvc.perform(put("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTask)))
                .andReturn();

        assertEquals(true,mvcResult.getResponse().getContentAsString().contains("This task is not belong to you."));
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldNotUpdateTaskCauseTaskNotFound() throws Exception {
        TaskEntity newTask = initializeTask();
        newTask.setCompleted(false); // Do some changes...
        newTask.setId(0);

        when(authTokenFilter.getUserId()).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(initializeUser()));
        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(newTask));

        MvcResult mvcResult = mockMvc.perform(put("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTask)))
                .andDo(print())
                .andReturn();

        assertEquals(true,mvcResult.getResponse().getContentAsString()
                .contains("Task with id -" + newTask.getId() + "- not found."));
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldDeleteTask() throws Exception {
        String expectedBody = initializeTask() + " deleted successfully.";

        commonMocks();

        MvcResult mvcResult = mockMvc.perform(delete("/api/tasks/{taskId}",1))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expectedBody,mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldNotDeleteTaskForAnotherUser() throws Exception {
        Task newTask = initializeDomainTask();
        newTask.setId(3);

        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(initializeTask()));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(initializeUser()));
        when(taskService.findById(anyInt())).thenReturn(newTask);

        MvcResult mvcResult = mockMvc.perform(delete("/api/tasks/{taskId}",3))
                .andReturn();

        assertEquals(true,mvcResult.getResponse().getContentAsString().contains("This task is not belong to you."));
    }

    @Test
    @WithMockUser(username = "ys",password = "123")
    void shouldNotDeleteTaskCauseTaskNotFound() throws Exception {
        TaskEntity newTask = initializeTask();

        when(authTokenFilter.getUserId()).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(initializeUser()));
        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(newTask));

        MvcResult mvcResult = mockMvc.perform(delete("/api/tasks/{taskId}",1))
                .andReturn();

        assertEquals(true,mvcResult.getResponse().getContentAsString()
                .contains("Task with id -" + newTask.getId() + "- not found."));
    }

    private UserEntity initializeUser() {
        return new UserEntity(1,"yaseen","123","yaseens@gmail.com","ys");
    }

    private User initializeDomainUser() {
        return new User(1,"yaseen","123","yaseens@gmail.com","ys",false);
    }


    private TaskEntity initializeTask() {
        return new TaskEntity(1,initializeUser(),"test",false,"2022-05-16 08:30:10","2022-05-16 08:50:00");
    }

    private Task initializeDomainTask() {
        return new Task(1,initializeDomainUser(),"test",false,"2022-05-16 08:30:10","2022-05-16 08:50:00");
    }


    private List<Task> initializeListOfTasks() {
        List<Task> taskList = new ArrayList<>();
        User user = initializeDomainUser();
        taskList.add(new Task(1,user,"do something...",true,"2022-05-16 08:30:10","2022-05-16 08:50:00"));
        taskList.add(new Task(2,user,"do something...",false,"2022-05-17 08:30:10","2022-05-17 08:50:00"));
        return taskList;
    }

    private URI getUrl(String direction) {
        MultiValueMap<String, Object> paraMap = new LinkedMultiValueMap<>();
        paraMap.add("page", Optional.of(0));
        paraMap.add("sortBy", Optional.of("start"));
        paraMap.add("pageSize", Optional.of(2));
        paraMap.add("sortDirection", Optional.of(direction));
        URI uri = UriComponentsBuilder.fromPath("/api/tasks")
                .queryParam("page", Optional.of(1))
                .queryParam("sortBy",Optional.of("start"))
                .queryParam("pageSize", Optional.of(3))
                .queryParam("sortDirection",Optional.of(direction))
                .build().toUri();
        return uri;
    }

    private MultiValueMap<String, Object> getParaMap(String direction) {
        MultiValueMap<String, Object> paraMap = new LinkedMultiValueMap<>();
        paraMap.add("page", Optional.of(0));
        paraMap.add("sortBy", Optional.of("start"));
        paraMap.add("pageSize", Optional.of(2));
        paraMap.add("sortDirection", Optional.of(direction));
        return paraMap;
    }

}
