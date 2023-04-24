package com.dhruza.physioconnectapi.auth.model;

import lombok.Getter;

@Getter
public enum RoleType {
    PRACTITIONER("PRACTITIONER"),
    PATIENT("PATIENT");

    private String name;

    RoleType(String name){
        this.name = name;
    }
}
