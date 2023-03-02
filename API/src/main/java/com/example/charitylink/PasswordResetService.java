package com.example.charitylink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    public void storeToken(String email) {
        User user = (User) userRepository.findUserIdByEmail(email);
        if (user != null) {
            String token = user.getPasswordResetToken();
            user.setPasswordResetToken(token);
            userRepository.save(user);
        }
    }
}
