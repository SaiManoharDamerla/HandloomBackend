package com.klef.jfsd.project.user.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Autowired
    private MailSender mailSender;

    private static final String OTP_CHARS = "0123456789";
    private static final int OTP_LENGTH = 6;

    // Method to generate a random OTP
    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARS.charAt(random.nextInt(OTP_CHARS.length())));
        }
        return otp.toString();
    }

    // Method to send an OTP email
    public String sendMail(String email) {
        String otp = generateOTP();
        String subject = "Your OTP Code";
        String body = "Your OTP for verification is: " + otp;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        mailSender.send(mailMessage);
		return otp;
    }
}
