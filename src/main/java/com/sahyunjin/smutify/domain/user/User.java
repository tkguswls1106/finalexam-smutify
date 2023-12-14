package com.sahyunjin.smutify.domain.user;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "user")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;


    @Builder(builderClassName = "UserAuthBuilder", builderMethodName = "UserAuthBuilder")
    public User(String username) {
        this.username = username;
    }
}