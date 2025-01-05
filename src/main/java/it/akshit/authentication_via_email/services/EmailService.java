package it.akshit.authentication_via_email.services;

import it.akshit.authentication_via_email.dtos.EmailDto;

public interface EmailService {

    void sendEmail(EmailDto emailDto);
}
