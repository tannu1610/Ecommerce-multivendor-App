package NayaBazzar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import NayaBazzar.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token);
}
