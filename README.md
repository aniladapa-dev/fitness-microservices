                             Fitness AI Application

A distributed, production-style fitness tracking platform built using a microservices architecture.

The system enables secure activity tracking, intelligent AI-powered workout insights, and scalable service communication using modern backend and frontend technologies.


Architecture Overview:

The application follows a microservices-based architecture, where each service is independently developed, deployed, and scaled.

Services communicate through:

    * Synchronous REST APIs
        
        - Gateway → Services

        - Activity Service → User Service 

    * Asynchronous messaging (RabbitMQ)

        - Activity Service → RabbitMQ → AI Service

This hybrid communication model ensures modularity, scalability, loose coupling, and fault tolerance.


Microservices Architecture:

1. Eureka Server (Service Discovery)

    * Registers all microservices dynamically

    * Enables service-to-service communication without hardcoded URLs

    * Supports load-balanced inter-service calls

2. Config Server (Centralized Configuration)

    * Manages externalized configuration for all services

    * Centralizes application properties

    * Allows configuration updates without rebuilding services

3. API Gateway

    * Single entry point for all client requests

    * Performs JWT validation

    * Routes requests to appropriate microservices

    * Handles CORS and security filters

4. User Service 

    * Manages user-related structured data

    * Handles daily targets (create, toggle, reset, delete)

    * Calculates confidence score

    * Supports auto-completion of targets

5. Activity Service 

    * Manages workout logging

    * Stores activity details (type, duration, calories, timestamps)

    * Publishes activity events to RabbitMQ

    * Calls User Service for auto-target completion

6. AI Service 

    * Consumes activity events from RabbitMQ

    * Generates AI-powered workout summaries

    * Stores recommendations linked to activity ID



Tech Stack:

    🔹 Backend:

        1. Spring Boot

        2. Spring Security

        3. RESTful Microservices

        4. RabbitMQ

        5. OAuth2 Authorization Code Flow with PKCE

        6. Keycloak

        7. WebClient (Inter-service communication)
    
    🔹 DataBases:

        1. PostgreSQL (Relational Data)

        2. MongoDB (Activity Data)

    🔹 Frontend:

        1. ReactJS

        2. Material UI
    
    🔹 AI Integration:

        1. Groq API (LLM-based activity insights)

    
Authentication & Security:
    
    * OAuth2 Authorization Code Flow with PKCE

    * Centralized identity management using Keycloak

    * JWT-based authentication

    * Token refresh mechanism

    * Protected frontend routes

    * Secure logout handling


Core Features:

    * Secure authentication and authorization using Keycloak (OAuth2 + PKCE)

    * Activity logging with type, duration, calories burned, and timestamp

    * Daily target creation, toggle, reset, and deletion

    * AI-generated personalized workout recommendations

    * Event-driven asynchronous communication using RabbitMQ

    * Microservices-based modular architecture

    * Responsive UI built with React and Material UI


AI Workflow:

    * User logs an activity

    * Activity Service validates user

    * Activity is saved in MongoDB

    * Activity event is published to RabbitMQ 

    * AI Service consumes the event

    * AI Service generates recommendation

    * Recommendation is stored in MongoDB

    * Frontend fetches and displays recommendation




    