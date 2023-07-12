package ch.axa.rest;


import ch.axa.rest.model.Task;
import ch.axa.rest.model.TaskRepositoryMock;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class PeopleRepositoryTest {

    private TaskRepositoryMock tasksRepositoryMock;

    @Before
    public void before() {
        tasksRepositoryMock = new TaskRepositoryMock();
    }

    @Test
    public void testStart() {
        assertEquals(5, tasksRepositoryMock.getTasks().size());
    }

    @Test
    public void testElement() {
        Optional<Task> task = tasksRepositoryMock.getTask(tasksRepositoryMock.getTasks().get(0).getId());
        assertTrue(task.isPresent());
        assertEquals(tasksRepositoryMock.getTasks().get(0).getId(), task.get().getId());
        assertTrue(task.get().getId() > 0);
    }

    @Test
    public void testAddPerson() {
        Task newTask = new Task(0, "task 01", true);
        Task addedPerson = tasksRepositoryMock.addTask(newTask);


        Optional<Task> task = tasksRepositoryMock.getTask(tasksRepositoryMock.getTasks().get(0).getId());
        assertTrue(task.isPresent());
        assertEquals(tasksRepositoryMock.getTasks().get(0).getId(), task.get().getId());
        assertTrue(task.get().getId() > 0);
    }

    @Test
    public void testDelete() {
        List<Task> listBefore = tasksRepositoryMock.getTasks();
        int sizeBefore = listBefore.size();
        List<Integer> idsBefore = listBefore.stream().map(task -> task.getId()).collect(Collectors.toList());

        int idDeleted = listBefore.get(0).getId();
        tasksRepositoryMock.deleteById(listBefore.get(0).getId());

        List<Task> listAfter = tasksRepositoryMock.getTasks();
        int sizeAfter = listAfter.size();
        List<Integer> idsAfter = listAfter.stream().map(task -> task.getId()).collect(Collectors.toList());

        assertTrue(sizeAfter == sizeBefore - 1);
        assertFalse(idsAfter.stream().anyMatch(id -> id == idDeleted));
    }

    @Test
    public void testDeleteAll() {
        List<Task> listBefore = new ArrayList<>(tasksRepositoryMock.getTasks());

        // delete all tasks
        listBefore.forEach(person -> assertTrue(tasksRepositoryMock.deleteById(person.getId()).isPresent()));

        List<Task> listAfter = tasksRepositoryMock.getTasks();

        assertEquals(0, listAfter.size());

        // try to delete first again
        assertFalse(tasksRepositoryMock.deleteById(listBefore.get(0).getId()).isPresent());

    }
}
