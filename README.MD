# Creditor App

**Authors:**
- Yehezkiel Stephanus Austin
- Rian Adriansyah

**URL (Local):** 

[http://localhost:8080/zk-pengajuan-leasing/pages/login.zul](http://localhost:8080/zk-pengajuan-leasing/pages/login.zul)

---

## Overview
Creditor App is a web application for managing creditors and loan submissions. The app provides dashboards, detailed views, and forms to manage both creditors and loans efficiently. Built with Java and ZK Framework, it uses PostgreSQL for data storage and Docker for easy deployment. Developed using **IntelliJ IDEA**.

---

## Features

- **Creditor Management**
    - View creditor dashboard with summary metrics
    - See detailed information for each creditor
    - Add or edit creditor information via forms

- **Loan Management**
    - View loan dashboard with summary metrics
    - Check detailed loan information
    - Add or edit loan applications via forms

- **User Management**
    - User registration and login
    - Secure authentication for accessing dashboards and forms

- **Responsive Web Interface**
    - Clean and intuitive UI using ZK Framework
    - CSS styling for authentication, dashboards, forms, and layouts

---

## Prerequisites

To run Creditor App, ensure you have the following installed:

- Docker
- Java JDK 8
- Maven (optional for building)
- DBeaver (optional, to view database tables and relationships)
- IntelliJ IDEA

**Technologies used:**
- ZK Framework (ZKOSS) for the web interface
- PostgreSQL for database management

---

## Database Setup

Default PostgreSQL credentials for Docker:

- **Database name:** zk-pengajuan-leasing
- **Username:** appuser
- **Password:** apppass

You can view tables and their relationships using **DBeaver** or any other database client.

---

## Running the App

1. Start PostgreSQL and the application using Docker:
```bash
docker-compose up -d
```
2. Run the application (using Maven Jetty plugin or your preferred IDE):
```bash
mvn clean:clean
mvn jetty:run
```
3. Open your browser:
```bash
http://localhost:8080/zk-pengajuan-leasing/pages/login.zul
```
4. Login or register to access the dashboards and forms.