# Java JDBC CRUD - Anime Database CLI

This is a command-line interface (CLI) application built with Java that demonstrates CRUD (Create, Read, Update, Delete) operations on a MySQL database using JDBC. The application allows users to manage a simple database of animes and their producers.

## ✨ Features

* **Interactive CLI:** A user-friendly, menu-driven interface to interact with the database.
* **Two Entities:** Full CRUD functionality for both `Anime` and `Producer` entities.
* **Database Connection Pooling:** Uses HikariCP for efficient and reusable database connections.
* **Clean Architecture:** The project is structured into layers (domain, repository, service) for better organization and maintenance.
* **Dockerized Database:** The MySQL database is managed using Docker Compose for easy setup and teardown.

## 🛠️ Technologies & Libraries Used

* **Java 21**
* **Maven** - Dependency Management
* **MySQL** - Relational Database
* **JDBC** - Java Database Connectivity API
* **Docker && Docker Compose** - Containerization
* **HikariCP** - High-performance JDBC connection pooling
* **Lombok** - To reduce boilerplate code (e.g., getters, setters, builders)
* **Log4j 2 && SLF4J** - For application logging

## 📋 Prerequisites

Before you begin, ensure you have the following installed on your system:
* **JDK 21** || newer
* **Apache Maven**
* **Docker** && **Docker Compose**

## 🚀 Getting Started

Follow these steps to get the application up and running.

### 1. Clone the Repository

```bash
git clone [https://github.com/your-username/jdbc-anime-crud.git](https://github.com/your-username/jdbc-anime-crud.git)
cd jdbc-anime-crud
```
*(Replace `your-username` and `jdbc-anime-crud` with your actual GitHub username and repository name.)*

### 2. Start the Database

The project includes a `docker-compose.yml` file to easily spin up a MySQL database instance.

```bash
docker-compose up -d
```
This command will download the MySQL image (if you don't have it) and start the database container in detached mode. Please wait about 30-60 seconds for the database to fully initialize.

### 3. Build the Project

Use Maven to compile the source code and download all the necessary dependencies.

```bash
mvn clean install
```

### 4. Run the Application

You can run the application directly from your favorite IDE (like IntelliJ or VS Code) by locating the `App.java` file and running its `main` method.

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
-- This schema is automatically created by MySQL inside the Docker container
-- based on the environment variables in docker-compose.yml.
-- You don't need to run this manually unless setting up the database outside of Docker.

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

---

*This README was generated with help from Gemini.*
