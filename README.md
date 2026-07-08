# Insurtech Backend

## About

Insurtech Backend is a Spring Boot service that handles core insurance claim operations, including AI-assisted claim estimation, document and file management, and related domain logic. The service is deployed on AWS infrastructure and is built for containerized, production-grade operation.

---

## Technologies

- Java, Spring Boot
- PostgreSQL (AWS RDS)
- AWS: EC2, S3, ECR, Secrets Manager / SSM
- Docker
- Maven
- GitHub Actions (CI/CD)

---

## Features

- AI-integrated claim estimation service for calculating insurance claim values
- Claim file and document upload, storage, and retrieval
- Transactional handling to ensure data consistency across claim operations
- Scheduled jobs for background claim processing tasks
- AWS S3 integration for document storage
- AWS Secrets Manager / SSM integration for secure configuration management
- Containerized deployment via Docker
- Automated build and deployment pipeline with GitHub Actions

---

## How to Run Locally

### 1. Clone the repository

```
git clone https://github.com/r-abdullayev/insurtech-backend.git
cd insurtech-backend
```

### 2. Set environment variables

Create a `.env` file (or configure `application.yml`) with the following:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:<port>/<database>
SPRING_DATASOURCE_USERNAME=<db_username>
SPRING_DATASOURCE_PASSWORD=<db_password>

AWS_ACCESS_KEY_ID=<aws_access_key_id>
AWS_SECRET_ACCESS_KEY=<aws_secret_access_key>
AWS_REGION=<aws_region>

AWS_S3_BUCKET_NAME=<s3_bucket_name>

SPRING_PROFILES_ACTIVE=local
```

### 3. Build with Maven

```
./mvnw clean install
```

### 4. Run with Docker

```
docker build -t insurtech-backend .
docker run --env-file .env -p 8080:8080 insurtech-backend
```

The container uses `entrypoint.sh` to start the application.

### 5. Access the service

Once running, the service is available at:

```
http://localhost:8080
```

---

## Author

Ramazan Abdullayev  
GitHub: https://github.com/r-abdullayev  
Telegram: https://t.me/ramazan_abdullayev  
LinkedIn: https://www.linkedin.com/in/ramazanabdu11ayev
