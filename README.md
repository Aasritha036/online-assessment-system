# Online Assessment System

## Live Demo

https://online-assessment-system-0nev.onrender.com/

## GitHub Repository

https://github.com/Aasritha036/online-assessment-system

## Project Overview

Online Assessment System is a web-based application developed using Spring Boot. It enables instructors to manage subjects, quizzes, and students while allowing students to participate in online assessments.

## Features

### Instructor
- Register and Login
- Manage Subjects
- Create Quizzes
- View Students
- Manage Assessments

### Student
- Register and Login
- View Available Quizzes
- Attempt Assessments
- View Results

## Technologies Used

### Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate

### Frontend
- Thymeleaf
- HTML
- CSS
- Bootstrap

### Database
- MySQL (Aiven Cloud)

### Build Tool
- Maven

### Deployment
- Docker
- Render

## Project Structure

```
src
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── templates
 ├── static
 └── resources
```

## Setup

1. Clone the repository

```bash
git clone https://github.com/Aasritha036/online-assessment-system.git
```

2. Configure Environment Variables

```
DB_URL
DB_USERNAME
DB_PASSWORD
```

3. Run

```bash
mvn spring-boot:run
```

## Future Improvements

- Spring Security
- Password Encryption (BCrypt)
- Email Verification
- JWT Authentication
- Unit Testing
- Admin Dashboard

## Author

**Aasritha**
