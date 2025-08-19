package com.example.ecommerce.serivce.email;

public interface EmailService {
    void sendRegistrationEmail(String to, String username);
    void sendPasswordResetEmail(String to, String username);
    void sendPasswordChangeEmail(String to, String username);
    void sendAccountDeletionEmail(String to, String username);
    void sendOrderConfirmationEmail(String to, String orderDetails);
    void sendOrderShipmentEmail(String to, String orderDetails);
}