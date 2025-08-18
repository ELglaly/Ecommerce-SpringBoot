package com.example.ecommerce.serivce.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordEmail implements IResetPasswordEmail {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String username) {
        if (to == null || username == null) {
            throw new IllegalArgumentException("Email parameters cannot be null");
        }
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("winformapp010@gmail.com");
            helper.setTo(to);
            helper.setSubject("Reset your password");
            helper.setText(createEmailBody(username), true); // Set to true to indicate HTML content
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String createEmailBody(String username) {
        return "<html>" +
                "<body>" +
                "<h1>Hello " + username + ",</h1>" +
                "<p>We received a request to reset your password. Please click on the link below to reset your password.</p>" +
                "<a href=\"http://localhost:8080/api/v1/users/reset-password-saved/" + username + "\">Reset Password</a>" +
                "<p>If you did not request to reset your password, please ignore this email.</p>" +
                "<p>Thank you,<br>ecommerce Team</p>" +
                "</body>" +
                "</html>";
    }
}
