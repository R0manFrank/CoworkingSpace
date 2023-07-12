package ch.axa.rest.model;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryMock {

    private final List<Task> tasks;

    public TaskRepositoryMock() {
        tasks = new ArrayList<Task>() {{
            add(new Task(1, "Lunch with Teo", true));
            add(new Task(2, "read, modern Java recipes", false));
            add(new Task(3, "change GUI on Tasks", true));
            add(new Task(4, "business Logic", false));
            add(new Task(5, "Lunch wit Jane", false));
        }};
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Optional<Task> getTask(int id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    public Task addTask(Task task) {
        int nextId = tasks.stream()
                .max(Comparator.comparingInt(Task::getId))
                .get()
                .getId() + 1;
        task.setId(nextId);
        tasks.add(task);
        return task;
    }

    public Optional<Task> deleteById(int id) {
        Optional<Task> task = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
        task.ifPresent(p -> tasks.remove(p));
        return task;
    }

}
