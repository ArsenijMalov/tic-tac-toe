
# 🚀 Distributed Tic-Tac-Toe Microservices

This project implements a distributed microservices architecture for a Tic-Tac-Toe game using Spring Boot, Eureka, Angular, and Docker Compose.

## 📌 Project Components
- **Eureka Server** – Service registry for microservices.
- **API Gateway** – Routes requests to microservices.
- **Game Session Service** – Manages game sessions.
- **Game Engine Service** – Handles game logic.
- **Frontend (Angular)** – User interface.

---

## 🔧 1. Installation & Setup

### 📌 1.1. Ensure Docker is installed
Check your Docker installation:

```sh
docker -v
docker-compose -v
```

If these commands don't work, install Docker.

### 📌 1.2. Start the project
📌 **Run all microservices with a single command:**

```sh
docker-compose up --build
```

This will:
- Build and start all containers.
- Set up networking between services.
- Launch the frontend at `http://localhost:4200`.

---

## 🔍 2. Verify the services
After starting, check the services:

| Component             | URL                                                                            |
|-----------------------|--------------------------------------------------------------------------------|
| 🛠 **Eureka Server**   | [http://localhost:8761](http://localhost:8761)                                 |
| 🌐 **API Gateway**     | [http://localhost:8080](http://localhost)                                      |
| 🎮 **Game Session**    | [http://localhost:8082/actuator/health](http://localhost:8082/actuator/health) |
| 🕹 **Game Engine**     | [http://localhost:8081/actuator/health](http://localhost:8081/actuator/health) |
| 🎨 **Frontend (UI)**   | [http://localhost:4200](http://localhost:4200)                                 |

If a microservice does not appear in Eureka, try restarting it:

```sh
docker-compose restart game-session-service
```

---

## ⚠️ 3. Troubleshooting

### ❌ 1. Cannot execute request on any known server
✔️ **Solution:**
- Ensure Eureka is running (`http://localhost:8761`).
- Check if `DISCOVERY_URL` is set inside the container:

```sh
docker exec -it game-session-service sh -c 'echo $DISCOVERY_URL'
```

**Expected output:**
```plaintext
http://eureka-server:8761/eureka/
```

### ❌ 2. `network not found: my-network`
✔️ **Solution:**

```sh
docker network prune  # Remove old networks
docker-compose down
docker-compose up --build
```

### ❌ 3. Angular frontend returns `404 Not Found`
✔️ **Solution:**
- Check if the frontend container is running:

```sh
docker ps | grep angular-frontend
```

- Restart the frontend:

```sh
docker-compose restart frontend
```

---


---

## 📌 4. Stopping and Cleaning Up
To stop all containers:

```sh
docker-compose down
```

To remove all unused Docker data:

```sh
docker system prune -a
```

---
