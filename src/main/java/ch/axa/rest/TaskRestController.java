package ch.axa.rest;

import ch.axa.rest.modelOld.Task;
import ch.axa.rest.modelOld.TaskRepository;
import ch.axa.rest.modelOld.TaskRepositoryMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TaskRestController {
    static Logger log = Logger.getAnonymousLogger();
    // @Autowired
    private final TaskRepositoryMock tasksRepository;
    private final TaskRepository tasksRepositoryDb;


    public TaskRestController(TaskRepositoryMock tasksRepository, TaskRepository tasksRepositoryDb) {
        this.tasksRepository = tasksRepository;
        this.tasksRepositoryDb = tasksRepositoryDb;
    }

    // Aufgabe 2 – Tasksliste laden
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                //.body(tasksRepository.getTasks());
                .body((List<Task>) tasksRepositoryDb.findAll());
    }

    // Aufgabe 3 - Einzelner Task laden
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id) {
//        Optional<Task> task = tasksRepository.getTask(id);
        Optional<Task> task = tasksRepositoryDb.findById((long) id);
        if (task.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP 200
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(task.get());
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }

    // Aufgabe 4 - Task hinzufügen
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    Task newTask(@RequestBody Task newTask) {

        return
                //tasksRepository.addTask(newTask);
                tasksRepositoryDb.save(newTask);
    }


    // Aufgabe 5 - Task bearbeiten
    @PutMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public ResponseEntity<Task> replaceTask(@RequestBody Task newTask, @PathVariable int id) {
//        Optional<Task> currentTask = tasksRepository.getTask(id);
        Optional<Task> currentTask = tasksRepositoryDb.findById((long) id);

        log.info(currentTask.toString());

        //tasksRepository.getTask(id)
        currentTask
                .map(task -> {
                    task.setDescription(newTask.getDescription());
                    task.setCompleted(newTask.isCompleted());
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            //.body(tasksRepository.addTask(task));
                            .body(tasksRepositoryDb.save(task));
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
//                            .body(tasksRepository.addTask(newTask));
                            .body(tasksRepositoryDb.save(newTask));
                });


       /* if (currentTask.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP 200
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(peopleRepository.addTask(currentTask.get()));

        } else {
            newTask.setId(id);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(peopleRepository.addPerson(newPerson));
        }*/


        return null;
    }

    // Aufgabe 5 - Task bearbeiten
    @PatchMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public ResponseEntity<Task> replacePatchTask(@RequestBody Task newTask, @PathVariable int id) {
//        Optional<Task> currentTask = tasksRepository.getTask(id);
        Optional<Task> currentTask = tasksRepositoryDb.findById((long) id);
        log.info(currentTask.toString());
        //tasksRepository.getTask(id)
        currentTask
                .map(task -> {
                    task.setDescription(newTask.getDescription());
                    task.setCompleted(newTask.isCompleted());
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(tasksRepository.addTask(task));
//                            .body(tasksRepositoryDb.save(newTask));
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(tasksRepository.addTask(newTask));
//                            .body(tasksRepositoryDb.save(newTask));
                });
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // not found empty
    }

    // Aufgabe 6 - Task löschen
/*    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Task> deleteTask(@PathVariable int id) {
//        if (tasksRepository.getTask(id).isPresent()) {
        if (tasksRepositoryDb.findById((long) id).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
//                    .body(tasksRepository.deleteById(id).get());
                    .body(tasksRepositoryDb.deleteById((long) id).get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)  // 404 not found
                    .build(); // empty body
        }


    }*/


    // Aufgabe 6 - Task löschen
    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Task> deleteTask(@PathVariable long id) {
        if (tasksRepositoryDb.findById(id).isPresent()) {
            tasksRepositoryDb.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)  // 404 not found
                    .build(); // empty body
        }


    }




    // Aufgabe 7 – Tasksliste löschen
    @DeleteMapping("/tasks")
    ResponseEntity<Iterable<Task>> deleteAllTasks() {
//        tasksRepository.getTasks().clear();
        tasksRepositoryDb.deleteAll(tasksRepositoryDb.findAll());
        return ResponseEntity
                .status(HttpStatus.OK)
//                .body(tasksRepository.getTasks());
                .body(tasksRepositoryDb.findAll());


    }

}
