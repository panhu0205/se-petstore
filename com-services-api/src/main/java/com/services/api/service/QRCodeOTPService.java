package com.services.api.service;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class QRCodeOTPService {
    private final SecureRandom secureRandom;

    public QRCodeOTPService() throws NoSuchAlgorithmException {
        secureRandom = SecureRandom.getInstance("SHA1PRNG");
    }

    public String generate(int maxLength) {
        final StringBuilder otp = new StringBuilder(maxLength);
        for (int i = 0; i < maxLength; i++) {
            otp.append(secureRandom.nextInt(9));
        }
        return otp.toString();
    }
}
