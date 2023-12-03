# JSON Web Token (JWT) authentication using Spring Security

## Introduction

JSON Web Token (JWT) is a widely used standard for token-based authentication. This implementation guide focuses on integrating JWT authentication into a Spring Security-enabled Java application.

## Prerequisites
Ensure you have the following prerequisites installed before setting up and running the project:

1. **Spring Boot 3**: This project is built on Spring Boot 3. Ensure you have Spring Boot 3 installed and configured.

2. **JDK 17**: Java Development Kit (JDK) version 17 is required to compile and run the project. Install JDK 17 and set up your environment variables accordingly.

3. **H2 Database**: This project uses the H2 in-memory database. Ensure you have H2 installed or configured in your Spring Boot application.

## Usage
This project exposes several endpoints to facilitate user authentication and access control:

1. `POST /api/auth/register`: Register a user to obtain JWT credentials. No JWT is required for this operation.

2. `POST /api/auth/login`: Obtain a JWT token for subsequent requests. No JWT is needed for this initial authentication.

3. `GET /api/user/info`: Access an endpoint that requires a valid JWT for authorization. Ensure the JWT is included in the Authorization header as "Bearer {JWT_TOKEN}".
