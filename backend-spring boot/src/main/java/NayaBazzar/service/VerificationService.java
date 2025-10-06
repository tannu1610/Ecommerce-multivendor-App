package NayaBazzar.service;

import NayaBazzar.model.VerificationCode;

public interface VerificationService {

    VerificationCode createVerificationCode(String otp, String email);
}
