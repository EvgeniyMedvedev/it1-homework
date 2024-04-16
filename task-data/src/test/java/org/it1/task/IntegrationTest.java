package org.it1.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.it1.task.entity.Task;
import org.it1.task.repo.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        Task aop = new Task();
        aop.setId(1L);
        aop.setDescription("Сделать ДЗ по Spring AOP");
        aop.setTitle("AOP Homework");
        aop.setCompleted(false);
        aop.setDueDate(LocalDateTime.now().plusDays(1));

        Task data = new Task();
        data.setDescription("Сделать ДЗ по Spring Data");
        data.setTitle("Data JPA Homework");
        data.setCompleted(false);
        data.setDueDate(LocalDateTime.now().plusDays(2));

        taskRepository.saveAll(List.of(aop, data));
    }
    @AfterEach
    public void postDestroy(){
        taskRepository.truncate();
    }

    @Test
    public void successGetAndDeleteTest() throws Exception {
        ResultActions mvcResult = mvc.perform(get("/api/task-data/tasks")
                .contentType(MediaType.APPLICATION_JSON));
        mvcResult.andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("AOP Homework")))
                .andExpect(jsonPath("$[1].title", is("Data JPA Homework")));

        var modelAndView = mvcResult.andReturn().getResponse();
        Task[] tasks = objectMapper.readValue(modelAndView.getContentAsString(), Task[].class);
        assert tasks != null;
        assert tasks.length != 0;

        Long id = tasks[0].getId();
        mvc.perform(get("/api/task-data/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("AOP Homework")));

        mvc.perform(delete("/api/task-data/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/task-data/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void successGetByIdTest() throws Exception {
        Task data = new Task();
        data.setDescription("Сделать уборку");
        data.setTitle("Уборка");
        data.setCompleted(false);
        data.setDueDate(LocalDateTime.now().plusWeeks(1));

        var resp = mvc.perform(post("/api/task-data/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(data)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("Уборка")))
                .andReturn().getResponse();
        Task task = objectMapper.readValue(resp.getContentAsString(), Task.class);

        mvc.perform(get("/api/task-data/tasks/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("Уборка")));

        data.setTitle("Клининг");
        mvc.perform(put("/api/task-data/tasks/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(data)))
                .andExpect(status().isOk());

        mvc.perform(get("/api/task-data/tasks/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("Клининг")));
    }
}
