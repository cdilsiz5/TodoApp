# TodoApp - Spring Boot Todo Application

## Project Information

This project is a **Todo** application developed using **Spring Boot** as the backend. 

### Technologies and Tools Used:
- **Java 19**: For application development.
- **Spring Boot 3.2.5**: For fast and easy backend development.
- **JWT (JSON Web Token)**: Used for user authentication and authorization.
- **Access Token**: Used for short-term authorization.
- **Refresh Token**: Used to renew the access token.
- **Maven**: For project and dependency management.
- **Docker**: For containerizing the application and distribution.
- **Docker Compose**: To run the application with its dependencies.
- **JUnit 5 & Mockito**: For writing unit tests and mocking.
- **Swagger (OpenAPI)**: For API documentation.

## Setup and Run Instructions

Follow these steps to build and run the project successfully.

### 1. Build the Application

Clone the project and build it using Maven:
 
Clone the project repository
```bash
git clone https://github.com/cdilsiz5/TodoApp.git
``` 
Navigate to the TodoApp directory

```bash
cd TodoApp
```
 Build the project using Maven

```bash
mvn clean install
```
### 2. Run the Todo App Application with Maven

To run the Todo App , use the following command:

Run Todo App
```bash
mvn spring-boot:run
```

### 3. Run the Test Suite with Maven

To run the unit tests in the project, use the following command:

Run all unit tests
```bash
mvn test
```
### 4. Push Docker Image to Docker Hub
Log in to Docker Hub and push the image:

Log in to Docker Hub
```bash
docker login
```

### 5. Build Docker Image
To containerize the project using Docker, you can build a Docker image with the following command:

Build the Docker image
```bash
docker build -t cihandsz/todo-app:latest .
```
This will create a Docker image of your project locally.


Push the Docker image
```bash
docker push cihandsz/todo-app:latest
```         
After this step, your image will be available on Docker Hub.

### 6. Start the Docker Container
To run the Docker container for your application, use the following command:

 Run the Docker container
```bash
docker run -p 8080:8080 cihandsz/todo-app:latest
```
This will start the application on port 8080.

### 7. Docker Compose Setup

Then, start the application and its dependencies using:
 
Navigate to the TodoApp directory
```bash
cd TodoApp
```
 Start the application with Docker Compose
```bash
docker-compose up --build
```
This will build and start the application within a Docker container, and map it to port 8080 on your local machine.

### 8. Access the Application
Once the application is up and running, you can access it at:

http://localhost:8080

 You can also view the Swagger API Documentation at:

http://localhost:8080/swagger-ui.html

 