package ch.axa.rest.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String phonenumber;
    private String password;
    private String role;
    private String salt;
    private String token;

    @OneToMany(mappedBy = "coUser")
    private List<Booking> bookings;
}
