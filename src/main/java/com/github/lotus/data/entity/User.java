package com.github.lotus.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.lotus.data.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "application_user")
@Getter @Setter
public class User extends AbstractEntity {

    @NotNull(message = "Имя не должно быть пустым")
    @NotBlank
    private String firstName;

    @NotNull(message = "Фамилия не должна быть пустой")
    @NotBlank
    private String surName;

    private String patronymic;

    @NotNull(message = "Логин не должен быть пустым")
    @NotBlank
    private String username;

    @JsonIgnore
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Lob
    @Column(length = 1000000)
    private byte[] profilePicture;
}
