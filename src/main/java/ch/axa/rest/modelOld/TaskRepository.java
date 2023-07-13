package ch.axa.rest.modelOld;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
     public Optional<Task> deleteById(long id);
}
