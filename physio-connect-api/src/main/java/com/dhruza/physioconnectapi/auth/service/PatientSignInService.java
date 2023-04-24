package com.dhruza.physioconnectapi.auth.service;

import com.dhruza.physioconnectapi.auth.model.UserRegistration;
import com.dhruza.physioconnectapi.model.Patient;
import com.dhruza.physioconnectapi.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class PatientSignInService implements SignInService{
    private final MailSender mailSender;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendRegistrationEmail(String email, String code) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("PhysioConnect Registration");
            msg.setText(
                    "Welcome to PhysioConnect! Your registration code is: "
                            + code);
            this.mailSender.send(msg);
        } catch (MailException e) {
            log.info(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void registerUser(UserRegistration userRegistration) {
        final Optional<Patient> patient = patientRepository.findPatientByEmailAndRegistrationCode(
                userRegistration.getEmail(), userRegistration.getRegistrationCode()
        );

        final Patient patientToRegister = patient.orElseThrow(
                () -> new SecurityException("Invalid registration credentials sent")
        );

        if(userRegistration.getPassword().equals(userRegistration.getRepeatPassword())){
            patientToRegister.setRegistrationComplete(true);
            patientToRegister.setPassword(
                    passwordEncoder.encode(userRegistration.getPassword())
            );
        }
    }
}
