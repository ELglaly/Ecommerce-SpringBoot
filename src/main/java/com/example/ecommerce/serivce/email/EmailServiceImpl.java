package com.example.ecommerce.serivce.email;

import com.example.ecommerce.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendRegistrationEmail(String to, String username) {
        if (to == null || username == null) {
            throw new IllegalArgumentException("Email parameters cannot be null");
        }
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("winformapp010@gmail.com");
            helper.setTo(to);
            helper.setSubject("Activate your account");
            helper.setText(createEmailBody(username), true); // Set to true to indicate HTML content
            emailSender.send(message);
        } catch (MailException | MessagingException e) {
            throw new EmailException("Failed to send email");
        }
    }
    /**
     * Creates the body of the registration email.
     *
     * @param username The username of the user registering.
     * @return The HTML content for the registration email.
     */
    private String createEmailBody(String username) {
        return "<html>" +
                "<body>" +
                "<h1>Hello " + username + ",</h1>" +
                "<p>Thank you for registering with us. Please click on the link below to activate your account.</p>" +
                "<a href=\"http://localhost:8080/api/v1/users/activate/" + username + "\">Activate Account</a>" +
                "<p>If you did not register with us, please ignore this email.</p>" +
                "<p>Thank you,<br>ecommerce Team</p>" +
                "</body>" +
                "</html>";
    }


    @Override
    public void sendPasswordResetEmail(String to, String username) {
            if (to == null || username == null) {
                throw new IllegalArgumentException("Email parameters cannot be null");
            }
            try {
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom("winformapp010@gmail.com");
                helper.setTo(to);
                helper.setSubject("Reset your password");
                helper.setText(createPasswordResetEmailBody(username), true); // Set to true to indicate HTML content
                emailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        /** * Creates the body of the password reset email.
         *
         * @param username The username of the user requesting a password reset.
         * @return The HTML content for the password reset email.
         */
        private String createPasswordResetEmailBody(String username) {
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



    @Override
    public void sendPasswordChangeEmail(String to, String username) {
        // Implementation for sending password change email
    }

    @Override
    public void sendAccountDeletionEmail(String to, String username) {
        // Implementation for sending account deletion email
    }

    @Override
    public void sendOrderConfirmationEmail(String to, String orderDetails) {
        // Implementation for sending order confirmation email
    }

    @Override
    public void sendOrderShipmentEmail(String to, String orderDetails) {
        // Implementation for sending order shipment email
    }
}
