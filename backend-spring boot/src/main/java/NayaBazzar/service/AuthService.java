package NayaBazzar.service;

import NayaBazzar.exception.SellerException;
import NayaBazzar.exception.UserException;
import NayaBazzar.request.LoginRequest;
import NayaBazzar.request.SignupRequest;
import NayaBazzar.response.AuthResponse;
import jakarta.mail.MessagingException;

public interface AuthService {

    void sentLoginOtp(String email) throws UserException, MessagingException;
    String createUser(SignupRequest req) throws SellerException;
    AuthResponse signin(LoginRequest req) throws SellerException;

}
