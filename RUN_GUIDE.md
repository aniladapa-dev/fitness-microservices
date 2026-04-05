# Fitness AI Microservices - Execution Guide

Follow these steps in order to launch the entire application environment:

### 1. Prerequisite Checklist
*   **Docker Desktop**: Ensure Docker is running on your system.
*   **Environment Variables**: Ensure you have a `.env` file in the root directory with your `GROQ_API_KEY`.
*   **Ports**: Check that ports `8080`, `8081`, `5173`, `8025`, `5432`, and `27017` are not being used by other applications.

### 2. Launch Infrastructure (Docker)
Open a terminal in the root folder of the project and run:
```bash
docker compose up -d --build
```
*Wait for a minute* to allow PostgreSQL, MongoDB, RabbitMQ, and Keycloak to initialize completely.

### 3. Start Frontend App
Open a **new** terminal in the `Frontend` directory and run:
```bash
npm install   # Only if running for the first time
npm run dev
```
The app will be available at [http://localhost:5173](http://localhost:5173).

### 4. Verify Services
*   **Keycloak Admin**: [http://localhost:8081/admin](http://localhost:8081/admin) (User: `admin` | Pass: `admin`)
*   **Mailhog (Emails)**: [http://localhost:8025](http://localhost:8025) (Check here for verification emails during Sign Up).
*   **Eureka (Discovery)**: [http://localhost:8761](http://localhost:8761) (To see if all backend services are "UP").

### 5. Final Application Flow
1.  Go to the Frontend ([http://localhost:5173](http://localhost:5173)).
2.  Click **Login** -> **Register** (as a new user).
3.  Check **Mailhog** for the verification email and click the link.
4.  Log in and start tracking your activities!
