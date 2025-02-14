# Ecommerce-SpringBoot

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)

## Overview

Ecommerce-SpringBoot is a Java-based e-commerce application built on the Spring Boot framework. This project aims to provide a robust and scalable solution for online shopping, featuring essential functionalities for both users and administrators. The application is designed to be modular and maintainable, making it easy to extend with additional features.

## Features

- **User Registration and Authentication**: 
  - Secure user sign-up and login using JWT (JSON Web Tokens).
  - Password encryption for enhanced security.

- **Product Management**: 
  - Admin capabilities to add, update, and delete products.
  - Product categorization and filtering options.

- **Shopping Cart**: 
  - Users can add products to their cart, view, and modify quantities.
  - Persistent cart functionality across sessions.

- **Order Processing**: 
  - Complete order management from cart to checkout.
  - Order history and tracking for users.

- **Image Management**: 
  - Upload and update product images efficiently.
  - Image storage and retrieval.

- **Admin Dashboard**: 
  - Overview of sales metrics, user statistics, and product inventory.

## Technologies Used

- **Java**: Core programming language.
- **Spring Boot**: Framework for building the application.
- **Spring Security**: For securing the application and managing authentication.
- **Hibernate**: ORM for database interactions.
- **MySQL**: Relational database for storing user and product data.
- **Maven**: Project management and build tool.
- **Thymeleaf**: For server-side rendering of web pages (if applicable).
- **RESTful API**: For communication between client and server.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/ELglaly/Ecommerce-SpringBoot.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd Ecommerce-SpringBoot
   ```

3. **Install dependencies**:
   ```bash
   mvn install
   ```

4. **Configure your database settings** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

5. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

## Configuration

- Ensure that your MySQL server is running and the database `ecommerce` is created.
- Adjust any other configurations in `application.properties` as needed, such as server port or logging levels.

## Usage

Once the application is running, you can access it via `http://localhost:8080`. 

### User Interface
- **Registration**: Users can create an account to start shopping.
- **Product Browsing**: Users can view available products and categories.
- **Cart Management**: Users can manage their shopping cart before checkout.
- **Admin Access**: Admins can log in to manage products and view statistics.

## API Endpoints

| Method | Endpoint                  | Description                       |
|--------|---------------------------|-----------------------------------|
| POST   | `/api/auth/register`      | Register a new user              |
| POST   | `/api/auth/login`         | Authenticate user                 |
| GET    | `/api/products`           | Retrieve all products             |
| POST   | `/api/products`           | Add a new product (Admin only)   |
| PUT    | `/api/products/{id}`      | Update product details (Admin)    |
| DELETE | `/api/products/{id}`      | Delete a product (Admin)          |
| GET    | `/api/orders`             | Retrieve user's order history      |

## Testing

To run tests, use the following command:
```bash
mvn test
```
Ensure that you have test cases written for your application logic.

## Contributing

Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them.
4. Push to your forked repository.
5. Submit a pull request for review.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Hibernate Documentation](https://hibernate.org/orm/documentation/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)

---

Feel free to customize any sections based on the specific features and functionalities of your project!
