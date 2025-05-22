#  E-commerce Platform


##  Project Information

**Project Name:** E-commerce Platform

**Project Description:**  
A robust and scalable e-commerce platform built with **Spring Boot**. This system enables users to browse products, manage their shopping cart, and complete secure purchases. It includes an **admin panel** for managing products, orders, and users. Designed with modern best practices, this project is ideal for businesses looking to establish or expand their online presence.

##  Features
- **User Authentication & Authorization** – Secure user registration, login, and role-based access with JWT.
- **Email Verification** – Send email verification links using JavaMailSender.
- **Product Management** – Admins can add, update, and delete products.
- **Shopping Cart** – Users can manage their cart and checkout securely.
- **Order Processing** – Integrated with Stripe/PayPal for payments.
- **Email Notifications** – Automated emails for registration and order updates.
- **RESTful API** – Well-documented API for frontend and third-party integrations.
- **Testing** – Unit and integration tests using JUnit 5 and Mockito.

## Technologies & Tools
**Languages:**  
- Java

**Frameworks & Libraries:**  
- Spring Boot, Spring Security, Spring Data JPA, Hibernate, JavaMailSender

**Database & Storage:**  
- MySQL (for production), H2 Database (for testing)

##  Project Structure
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

##  Installation & Setup
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


## Authentication
All API endpoints require authentication via a Bearer token.

---

## API Endpoints

### 1. User Authentication

#### **POST /api/register**
**Description:** Registers a new user.
- **Request Body:**
  ```json
  {
    "username": "string",
    "password": "string",
    "email": "string"
  }
  ```
- **Response:**
  ```json
  {
    "data": { "user details" },
    "message": "User created successfully"
  }
  ```

#### **POST /api/login**
**Description:** Logs in a user and returns an authentication token.
- **Request Body:**
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response:**
  ```json
  {
    "data": "JWT Token",
    "message": "Login successful"
  }
  ```

#### **GET /api/login/google**
**Description:** Login with Google.
- **Response:**
  ```json
  {
    "data": "Login with Google",
    "message": "Success"
  }
  ```

---

### 2. Product Management

#### **POST /api/products**
**Description:** Create a new product.
- **Request Body:**
  ```json
  {
    "name": "Product Name",
    "price": 100.0,
    "category": "Category"
  }
  ```
- **Response:**
  ```json
  {
    "data": { "product details" },
    "message": "Product created successfully"
  }
  ```

#### **GET /api/products/{id}**
**Description:** Get product by ID.
- **Response:**
  ```json
  {
    "data": { "product details" },
    "message": "Product found"
  }
  ```

#### **GET /api/products**
**Description:** Get all products.
- **Response:**
  ```json
  {
    "data": [ { "product details" } ],
    "message": "Products retrieved successfully"
  }
  ```

#### **PUT /api/products/{id}**
**Description:** Update a product.
- **Request Body:**
  ```json
  {
    "name": "Updated Product Name",
    "price": 150.0,
    "category": "Updated Category"
  }
  ```
- **Response:**
  ```json
  {
    "data": { "updated product details" },
    "message": "Product updated successfully"
  }
  ```

#### **DELETE /api/products/{id}**
**Description:** Delete a product.
- **Response:**
  ```json
  {
    "data": true,
    "message": "Product deleted successfully"
  }
  ```

---

### 3. Order Management

#### **POST /api/orders/by-cart/{cartId}**
**Description:** Place an order by cart ID.
- **Response:**
  ```json
  {
    "data": { "order details" },
    "message": "Order created successfully"
  }
  ```

#### **POST /api/orders/by-product**
**Description:** Place an order by product.
- **Request Body:**
  ```json
  {
    "productId": 1,
    "quantity": 2
  }
  ```
- **Response:**
  ```json
  {
    "data": { "order details" },
    "message": "Order created successfully"
  }
  ```

#### **GET /api/orders/{orderId}**
**Description:** Get order by ID.
- **Response:**
  ```json
  {
    "data": { "order details" },
    "message": "Order retrieved successfully"
  }
  ```

#### **GET /api/orders**
**Description:** Get all orders.
- **Response:**
  ```json
  {
    "data": [ { "order details" } ],
    "message": "Orders retrieved successfully"
  }
  ```

---

### 4. Cart Management

#### **GET /api/carts/{cartId}**
**Description:** Get cart by ID.
- **Response:**
  ```json
  {
    "data": { "cart details" },
    "message": "Cart retrieved successfully"
  }
  ```

#### **POST /api/carts/{cartId}**
**Description:** Add product to cart.
- **Request Params:**
  - `productId: Long`
  - `quantity: int`
- **Response:**
  ```json
  {
    "data": { "cart details" },
    "message": "Product added to cart successfully"
  }
  ```

#### **DELETE /api/carts/item/{itemId}/{cartId}**
**Description:** Remove item from cart.
- **Response:**
  ```json
  {
    "data": null,
    "message": "Item removed successfully"
  }
  ```

---

## Error Handling
- **400:** Bad Request (Invalid input)
- **401:** Unauthorized (Invalid credentials)
- **403:** Forbidden (Admin only access)
- **404:** Not Found (Resource does not exist)
- **500:** Internal Server Error

##  Testing & Debugging
Run all tests:
```sh
mvn test
```
