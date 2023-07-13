package ch.axa.rest.modelOld;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskUser {

    @Id
    private int id;
    private String username;
    private String password;  // hashed pw
    private String token;
    public TaskUser(int id, String username, String hashCode) {
        this.id = id;
        this.username = username;
        this.password = hashCode;
    }


}
