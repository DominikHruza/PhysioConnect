package com.dhruza.physioconnectapi.auth.service;

import com.dhruza.physioconnectapi.auth.model.UserRegistration;

public interface SignInService {
    void sendRegistrationEmail(String email, String code);
    void registerUser(UserRegistration userRegistration);
}
