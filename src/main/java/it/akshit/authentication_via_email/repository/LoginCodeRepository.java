package it.akshit.authentication_via_email.repository;

import it.akshit.authentication_via_email.models.LoginCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoginCodeRepository extends JpaRepository<LoginCode, UUID> {
    Optional<LoginCode> findByCode(String code);
}
