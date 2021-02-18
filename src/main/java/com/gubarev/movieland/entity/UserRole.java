package com.gubarev.movieland.entity;

import lombok.AllArgsConstructor;

import java.util.EnumSet;

@AllArgsConstructor
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String name;

    public String getName() {
        return name;
    }

    public static UserRole getUserRole(String name) {
        return EnumSet.allOf(UserRole.class)
                .stream()
                .filter(role -> role.name.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", name)));
    }
}
