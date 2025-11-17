# Blackjack Reactive API – Dockerized Version

## 📄 Description

This project is a fully reactive **Blackjack Game API** built using **Spring Boot WebFlux**, **MySQL (R2DBC)**, and **MongoDB Reactive**. The goal of the exercise is to create a complete reactive system, integrate two databases, implement proper exception handling, write tests, document the API, and finally **dockerize the entire project**.

---

## 💻 Technologies Used

* Java 21
* Spring Boot 3 (WebFlux, R2DBC, Reactive MongoDB)
* MySQL 8 (Docker)
* MongoDB Atlas or local MongoDB
* Docker & Docker Compose
* Maven
* Swagger / OpenAPI (springdoc)
* Postman (for testing)
* JUnit 5 & Mockito
* Lombok

---

## 📋 Requirements

To run the project locally you must have:

* **Java 21**
* **Maven 3.9+**
* **Docker & Docker Compose**
* A **MySQL server** (Docker or local)
* A **MongoDB connection string** (Atlas or local)

---

## 🛠️ Installation

Clone the repository:

```bash
git clone <your-repo-url>
cd <your-project-folder>
```

Build the application:

```bash
mvn clean package -DskipTests
```

---

## ▶️ Running the Project Locally

### Option 1 — Local MySQL (XAMPP or similar)

1. Start MySQL locally
2. Create the database manually:

```sql
CREATE DATABASE blackjack;
```

3. Run the Spring Boot app:

```bash
mvn spring-boot:run
```

---

## 🌐 Running with Docker

### 1️⃣ Build the Docker image

```bash
docker build -t blackjack-api .
```

### 2️⃣ Run the container

```bash
docker run -p 8080:8080 --name blackjack-container blackjack-api
```

### 3️⃣ Run using Docker Compose (recommended)

```bash
docker compose up --build
```

This will:

* Start **MySQL** in Docker
* Start the **Blackjack API**
* Automatically create the `player` table using `init.sql`
* Expose the API on: **[http://localhost:8080](http://localhost:8080)**

---

## 📘 Swagger / OpenAPI Documentation

Once the application is running:

📌 Open Swagger UI here:

```
http://localhost:8080/swagger-ui.html
```

You can test all endpoints directly from Swagger.

---

## 🧪 Postman Examples

Below are example requests to test your Blackjack API.

### ✔️ Create a new player

**POST** `http://localhost:8080/player`

```json
{
  "name": "Carlos de Cózar Ruiz-Salinas"
}
```

### ✔️ Start a Blackjack game for a player

**POST** `http://localhost:8080/game/start/{playerId}`

Example:

```
POST http://localhost:8080/game/start/1
```

### ✔️ Draw a card

**POST** `http://localhost:8080/game/hit/{playerId}`

### ✔️ End the turn (stand)

**POST** `http://localhost:8080/game/stand/{playerId}`

### ✔️ Get player history (MongoDB)

**GET** `http://localhost:8080/history/{playerId}`

### ✔️ List all players

**GET** `http://localhost:8080/player`

---

## 📦 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── cat/itacademy/s05/t01/n01/blackjack/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── domain/
│   │       │   ├── mongo/
│   │       │   └── sql/
│   │       ├── dto/
│   │       │   ├── request/
│   │       │   └── response/
│   │       ├── exception/
│   │       ├── repository/
│   │       │   ├── mongo/
│   │       │   └── sql/
│   │       ├── service/
│   │       ├── utils/
│   │       └── BlackjackApplication.java
│   │
│   └── resources/
│       └── application.yml
│     
│
└── test/
    └── java/
        └── cat/itacademy/s05/t01/n01/blackjack/
            ├── game/
            │   ├── BlackjackControllerTest.java
            │   └── BlackjackGameServiceTest.java
            ├── player/
            │   ├── PlayerControllerTest.java
            │   └── PlayerServiceTest.java
            └── BlackjackApplicationTests.java

```

## 🤝 Contributing

Pull requests are welcome.
Please open an issue before submitting changes.

---

## 🏁 Final Notes

* The project is fully reactive (WebFlux + R2DBC + MongoDB Reactive)
* Docker simplifies the entire environment setup
* Swagger and Postman allow you to test all API actions easily

Enjoy your Blackjack API 🎲🃏
