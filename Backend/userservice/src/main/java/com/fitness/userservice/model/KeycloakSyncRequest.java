package com.fitness.userservice.model;

import lombok.Data;

@Data
public class KeycloakSyncRequest {
    private String keycloakId;
    private String email;
    private String firstName;
    private String lastName;
}
