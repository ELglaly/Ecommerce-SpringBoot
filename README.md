# 🛍️ E-commerce Platform

![E-commerce Banner](https://via.placeholder.com/1000x300?text=E-commerce+Platform)

## 📌 Project Information

**Project Name:** E-commerce Platform

**Project Description:**  
A robust and scalable e-commerce platform built with **Spring Boot**. This system enables users to browse products, manage their shopping cart, and complete secure purchases. It includes an **admin panel** for managing products, orders, and users. Designed with modern best practices, this project is ideal for businesses looking to establish or expand their online presence.

## 🚀 Features
- **🔐 User Authentication & Authorization** – Secure user registration, login, and role-based access with JWT.
- **📧 Email Verification** – Send email verification links using JavaMailSender.
- **🛒 Product Management** – Admins can add, update, and delete products.
- **🛍️ Shopping Cart** – Users can manage their cart and checkout securely.
- **💳 Order Processing** – Integrated with Stripe/PayPal for payments.
- **📨 Email Notifications** – Automated emails for registration and order updates.
- **🖥️ RESTful API** – Well-documented API for frontend and third-party integrations.
- **🧪 Testing** – Unit and integration tests using JUnit 5 and Mockito.

## 🎯 Target Audience
- **🛍️ Online Retailers** – Businesses expanding their e-commerce presence.
- **💻 Developers** – A robust backend to build upon.
- **📚 Students & Learners** – Learn **Spring Boot**, REST APIs, and secure app development.

## 🛠️ Technologies & Tools
**Languages:**  
- Java

**Frameworks & Libraries:**  
- Spring Boot, Spring Security, Spring Data JPA, Hibernate, JavaMailSender

**Database & Storage:**  
- MySQL (for production), H2 Database (for testing)

**APIs & Integrations:**  
- Stripe/PayPal, SMTP (e.g., Gmail SMTP)

## 🏗️ Project Structure
```
📂 src
 ├── 📂 main
 │   ├── 📂 java
 │   │   ├── 📂 com.example.Ecommerce
 │   │       ├── 📂 aspect
 │   │       ├── 📂 config
 │   │       ├── 📂 constants
 │   │       ├── 📂 controller
 │   │       ├── 📂 enums
 │   │       ├── 📂 exceptions
 │   │       ├── 📂 factory
 │   │       ├── 📂 mapper
 │   │       ├── 📂 model
 │   │       ├── 📂 repository
 │   │       ├── 📂 request
 │   │       ├── 📂 response
 │   │       ├── 📂 scheduler
 │   │       ├── 📂 security
 │   │       ├── 📂 service
 │   │       ├── 📂 util
 │   │       ├── 📂 validator
 │   │       ├── 📄 DemoApplication.java
 │   │       ├── 📄 DummyObjects.java
 │   ├── 📂 resources
 │   ├── 📂 test
 ├── 📂 target
 ├── 📄 .gitattributes
```

## 📥 Installation & Setup
### Prerequisites
- Java Development Kit (JDK) 11+
- Maven
- MySQL
- SMTP Server (for email notifications)

### Installation Steps
```sh
git clone https://github.com/ELglaly/Ecommerce-SpringBoot.git
cd Ecommerce-SpringBoot
```
#### Configure Database
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```
#### Configure Email Settings
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_email_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
#### Build & Run
```sh
mvn clean install
mvn spring-boot:run
```
#### Access the Application
[http://localhost:8080](http://localhost:8080)

## 🔍 API Endpoints
### User Authentication
```sh
POST /api/auth/register
```
```json
{
  "username": "user1",
  "email": "user1@example.com",
  "password": "password123"
}
```
### Fetch Products
```sh
GET /api/products
```
### Add to Cart
```sh
POST /api/cart/add
```
```json
{
  "productId": 1,
  "quantity": 2
}
```
### Place Order
```sh
POST /api/orders
```
```json
{
  "cartId": 1,
  "paymentMethod": "credit_card"
}
```

## 🧪 Testing & Debugging
Run all tests:
```sh
mvn test
```

## 📜 Contribution Guidelines
1. **Fork** the repository.
2. **Create a feature branch:**
   ```sh
   git checkout -b feature/your-feature-name
   ```
3. **Commit changes:**
   ```sh
   git commit -m "Add your feature"
   ```
4. **Push to branch:**
   ```sh
   git push origin feature/your-feature-name
   ```
5. **Open a pull request.**
