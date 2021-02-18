package com.gubarev.movieland.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private long id;
    private String name;
    private String email;
    private String password;

    public User(long id, String name, String email, String password, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @Enumerated(EnumType.STRING)
    @Formula("(select ur.role from userrole ur where ur.id = userRoleId)")
    private UserRole userRole;
}
