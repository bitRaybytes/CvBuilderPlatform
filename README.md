# CV Builder Platform

A REST API for building and exporting professional CVs. Built with Spring Boot, secured with JWT, backed by PostgreSQL, and containerized with Docker.

---

## Tech Stack

- **Java 21** + **Spring Boot 4**
- **Spring Security** with JWT Authentication
- **PostgreSQL** (via Docker)
- **iText 5** for PDF export
- **Lombok**
- **Maven**

---

## Getting Started

### Prerequisites

- Java 21
- Maven
- Docker + Docker Compose
- OpenSSL (for key generation)

---

### 1. Clone the repository

```bash
git clone https://github.com/dein-name/cv-builderplatform.git
cd cv-builderplatform
```

---

### 2. Configure environment variables

Copy the example file and rename it:

```bash
cp .env.example .env
```

Open `.env` and fill in your values. To generate a new JWT secret key run:

```bash
echo "$(openssl rand -base64 64 | tr -d '\n','\+','\/','\-')" 
```

Copy the output and set it as the value of `JWT_SECRET` in your `.env` file.

---

### 3. Start the application

```bash
docker compose up --build
```

This starts both the PostgreSQL database and the Spring Boot application.

The API is available at:

```text
http://localhost:8080
```

The frontend is served at:

```text
http://localhost:8080/index.html
```

---

## Environment Variables

| Variable | Description | Example |
|---|---|---|
| `JWT_SECRET` | Base64 encoded secret key for JWT signing | `dGVzdHNlY3JldA==` |
| `DB_URL` | JDBC connection URL | `jdbc:postgresql://postgres:5432/cvbuilder` |
| `DB_USER` | Database username | `postgres` |
| `DB_PASSWORD` | Database password | `postgres` |

See `.env.example` for a full template.

---

## API Overview

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Login and receive JWT token |

### CV

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/cv` | Load full CV |
| `GET` | `/api/cv/pdfExport/` | Export CV as PDF |

### Sections (all require Bearer token)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/cv/personals` | Save personal info |
| `DELETE` | `/api/cv/personals` | Delete personal info |
| `POST/PUT/DELETE` | `/api/cv/experiences/{id}` | Manage experiences |
| `POST/PUT/DELETE` | `/api/cv/educations/{id}` | Manage educations |
| `POST/PUT/DELETE` | `/api/cv/skills/{id}` | Manage skills |
| `POST/PUT/DELETE` | `/api/cv/certificates/{id}` | Manage certificates |
| `POST/PUT/DELETE` | `/api/cv/internships/{id}` | Manage internships |
| `POST/PUT/DELETE` | `/api/cv/volunteers/{id}` | Manage volunteer work |
| `POST/PUT/DELETE` | `/api/cv/hobbies/{id}` | Manage hobbies |
| `POST/PUT/DELETE` | `/api/cv/signature` | Manage signature |

All protected endpoints require the following header:

```text
Authorization: Bearer <your_token>
```

---

## Project Structure

```text
src/
├── main/
│   ├── java/com/example/cv_builderplatform/
│   │   ├── config/          # Security and CORS configuration
│   │   ├── controller/      # REST controllers
│   │   ├── dto/             # Data transfer objects
│   │   ├── entities/        # JPA entities
│   │   ├── mapper/          # Entity ↔ DTO mappers
│   │   ├── repositories/    # Spring Data JPA repositories
│   │   ├── security/        # JWT filter, provider, user details
│   │   └── services/        # Business logic
│   └── resources/
│       ├── static/          # Frontend (HTML, CSS, JS)
│       └── application.properties
└── test/
    └── resources/
        └── application-test.properties
```

---

## Running Tests

Make sure Docker is running and the database is up, then:

```bash
mvn test
```

Tests use a separate configuration defined in `src/test/resources/application-test.properties`.

## Testing with Postman

Import the collection from:
[docs/cv-builder.postman_collection.json](docs/cv-builder.postman_collection.json)

1. Register a user via `POST /api/auth/register`
2. Login via `POST /api/auth/login` and copy the `accessToken`
3. Set the token in the Authorization header as `Bearer <token>`

---

## Example Payload

A full CV example payload for testing the API can be found here:
[docs/cv-example.json](docs/cv-example.json)

## Notes

- `spring.jpa.hibernate.ddl-auto` is set to `create` - the schema is rebuilt on every start. Change to `update` when you want to persist data across restarts.
- The `.env` file is listed in `.gitignore` and will never be committed to the repository.
- PDF export uses iText 5 and generates a downloadable `lebenslauf.pdf`.
