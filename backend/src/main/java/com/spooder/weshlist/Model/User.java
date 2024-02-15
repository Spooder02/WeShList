package com.spooder.weshlist.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key_id;

    @Column(nullable = false, unique = true)
    private String ID;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Email(message = "Email is not valid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String tel;

    @Column(nullable = false)
    private int point;

    @Column
    @JsonIgnore
    private boolean activated;

    @Column
    private String role;
}
