# Java JDBC CLI CRUD - Anime Database

This Java-based CLI project demonstrates full CRUD operations on a MySQL database using JDBC. Built with a layered architecture and Dockerized database, it’s designed as a practical exercise to consolidate Java backend skills in a real-world context.

---

## 🎯 Project Goals & Motivation

This project was created to strengthen my backend development skills using core Java technologies. It reflects my hands-on approach to learning by building real solutions and applying best practices commonly seen in enterprise systems.

Through this experience, I aimed to:

- Understand **how Java interacts with relational databases** using JDBC.
- Learn and apply **design patterns and software architecture** principles like separation of concerns.
- Embrace **DevOps tools** like Docker for reproducible environments.
- Simulate a realistic workflow found in backend projects using Java and SQL.

---

## 🚀 What I Learned & Practiced

### ✅ Core Java & JDBC

- Wrote raw SQL queries for Create, Read, Update, and Delete operations.
- Used `PreparedStatement` to avoid SQL injection and improve security.
- Handled `SQLException` and managed JDBC resources properly with try-with-resources.

### ✅ Software Design & Architecture

- Applied a **layered architecture** (Domain → Repository → Service → Application).
- Promoted separation of concerns, making the codebase cleaner and easier to maintain.
- Modeled domain entities (`Anime`, `Producer`) with strong encapsulation.

### ✅ Database & Environment Setup

- Used **Docker + Docker Compose** to spin up and manage a MySQL instance.
- Configured automatic database schema setup through container initialization.

### ✅ Advanced Practices

- **Maven** for managing dependencies
- Integrated **HikariCP** for efficient connection pooling.
- Used **Lombok** to reduce boilerplate (e.g., builders, getters).
- Employed **SLF4J + Log4j 2** for structured logging.
- Worked with `Optional`, streamlining null handling and improving code clarity.

---

This codebase can be expanded in the future to include features like:

- Web layer with Spring Boot
- Unit tests (JUnit + Mockito)
- Deployment to a cloud provider (e.g., Heroku, AWS)

---
## 💻 How to Use

Once the application is running, you will see the main menu in your console:

```
--------------------
Select an option:
1. Anime CRUD
2. Producer CRUD
0. Exit
--------------------
```
Simply type the number corresponding to your choice and press `Enter` to navigate through the menus and perform CRUD operations.

**Example: Finding all Animes**
1.  Enter `1` to go to the Anime menu.
2.  Enter `1` again to find all animes.
3.  The console will display a list of all animes currently in the database.

## 📂 Project Structure

The project follows a layered architecture to separate concerns:

```
src/main/java/org
├── application/   # Main application entry point (App.java)
├── connection/    # Database connection factory (ConnectionFactory.java)
├── domain/        # Data models (Anime.java, Producer.java)
├── repository/    # Data Access Layer (interacts directly with the DB)
└── service/       # Business Logic Layer (contains application rules)
```

## 📝 Database Schema

The application uses two tables: `producer` and `anime`. The `anime` table has a foreign key relationship with the `producer` table.

```sql

CREATE DATABASE IF NOT EXISTS anime_store;
USE anime_store;

CREATE TABLE producer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE anime (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    episodes INT NOT NULL,
    producer_id INT NOT NULL,
    FOREIGN KEY (producer_id) REFERENCES producer(id)
);

```

## 📬 Final Thoughts

This project was more than just a CRUD system – it was a journey of exploration through Java’s ecosystem. It helped me understand backend software construction from scratch and solidified my interest in backend engineering using Java.

