package ch.axa.rest.modelOld;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity Task
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {

    @Id
    private int id;
    private String description;
    private boolean completed;

}
