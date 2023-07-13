package ch.axa.rest;

import ch.axa.rest.modelOld.Task;
import ch.axa.rest.modelOld.TaskRepository;
import ch.axa.rest.modelOld.TaskUser;
import ch.axa.rest.modelOld.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestApplication {
    private static final Logger log = LoggerFactory.getLogger(RestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }


    @Bean
    public CommandLineRunner init(TaskRepository repository, UserRepository userRepository) {
        //https://spring.io/guides/gs/accessing-data-jpa/
        return (args) -> {
            // save a couple of tasks
            repository.save(new Task(1, "Lunch with Teodor", true));
            repository.save(new Task(2, "read, modern Java recipes", false));
            repository.save(new Task(3, "change GUI on Tasks", true));
            repository.save(new Task(4, "business Logic", false));
            repository.save(new Task(5, "Lunch wit Jane", false));

            // fetch all tasks
            log.info("Products found with findAll()");
            log.info("----------------------------");
            for (Task task : repository.findAll()) {
                log.info(task.toString());
            }
            log.info("end findAll()");
            // fetch an individual product by Id
            repository.findById(1L)
                    .ifPresent(product -> {
                        log.info("Product find with findById(1L)");
                        log.info("------------------------------");
                        log.info(product.toString());
                        log.info("");
                    });
            // fetch products by name
	/*		log.info("Product found by Name ('Couch Sofia')");
			log.info("-------------------------------------");
			repository.findByName("Couch Sofia").forEach(couch ->    {
				log.info(couch.toString());
			});
			for (Product couch : repository.findByName("Couch Sofia")) {
				log.info(couch.toString());
			}
            log.info("");
*/


            // save a couple of users

            userRepository.save(new TaskUser(1, "user1", HashCode.generateHashCode("123")));
            userRepository.save(new TaskUser(2, "user2", HashCode.generateHashCode("123")));
            userRepository.save(new TaskUser(3, "user3", HashCode.generateHashCode("123")));
            userRepository.save(new TaskUser(4, "admin", HashCode.generateHashCode("123")));

            // fetch all users
            log.info("Users found with findAll()");
            log.info("----------------------------");
            for (TaskUser user : userRepository.findAll()) {
                log.info(user.toString());
            }
            log.info("end findAll()");
            // fetch an individual product by Id
            userRepository.findById(1L)
                    .ifPresent(user -> {
                        log.info("User find with findById(1L)");
                        log.info("------------------------------");
                        log.info(user.toString());
                        log.info("");
                    });

        };
    }


}
