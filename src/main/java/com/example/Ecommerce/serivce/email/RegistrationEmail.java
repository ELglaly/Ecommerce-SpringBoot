package com.example.Ecommerce.serivce.email;

        import jakarta.mail.MessagingException;
        import jakarta.mail.internet.MimeMessage;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.mail.MailException;
        import org.springframework.mail.javamail.JavaMailSender;
        import org.springframework.mail.javamail.MimeMessageHelper;
        import org.springframework.stereotype.Component;

        @Component
        public class RegistrationEmail implements EmailService {

            @Autowired
            private JavaMailSender emailSender;

            @Override
            public void sendSimpleMessage(String to, String username) {
                if (to == null) {
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
                    throw new RuntimeException("Failed to send email", e);
                }
            }

            private String createEmailBody(String username) {
                return "<html>" +
                        "<body>" +
                        "<h1>Hello " + username + ",</h1>" +
                        "<p>Thank you for registering with us. Please click on the link below to activate your account.</p>" +
                        "<a href=\"http://localhost:8080/api/v1/users/activate/" + username + "\">Activate Account</a>" +
                        "<p>If you did not register with us, please ignore this email.</p>" +
                        "<p>Thank you,<br>Ecommerce Team</p>" +
                        "</body>" +
                        "</html>";
            }
        }