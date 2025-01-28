# Employee REST API

This is a Spring Boot application that provides a REST API for performing CRUD operations on an Employee entity. The API supports creating, reading, updating, and deleting employees, with validation and exception handling.

## Features
- CRUD Operations: Create, Read, Update, and Delete employees.
- Validation: Input validation for employee fields (e.g., name, age, gender, city, pin code).
- Exception Handling: Custom error responses for validation errors, resource not found, and internal server errors.
- DTOs: Use of Data Transfer Objects (DTOs) for request and response handling.
- Lombok: Reduces boilerplate code with Lombok annotations.
- MySQL Database: Stores employee data in a MySQL database.
- Enhanced Logging
  - Correlation ID  
    - A unique ID is generated for each request and included in every log message.
    - This helps track requests across multiple microservices.
  - Execution Time
    - The start time, end time, and execution duration of each method are logged.
  - Centralized Logging
    - Logging logic is centralized in the LoggingAspect, making it easy to manage and update.
  - Thread Safety
    - The correlation ID is stored in the MDC, which is thread-safe and automatically cleared after the request is processed.

## Technologies Used

- Spring Boot: Backend framework.
- MySQL: Database for storing employee data.
- Lombok: Simplifies Java code with annotations.
- Spring Data JPA: For database operations.
- Spring Validation: For request validation.
## Setup and Installation
## Prerequisites

- Java 17 or higher
- MySQL Server
- Maven
- Postman (for testing)

## Steps to Run the Project
## Clone the Repository:

```
    git clone https://github.com/sekharnatw/EmployeeCrudhub
```

## Database configuration
Create a MySQL database with the name `springbootdb` and add the credentials to `/resources/application.properties`.  
The default ones are :

```
spring.datasource.url=jdbc:mysql://localhost:3306/springbootdb
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```
## Build and Run the Application:

```
    mvn clean install
    mvn spring-boot:run
```
## Access the API:
- The API will be available at http://localhost:8080/api/employees. 

## API Endpoints
| HTTP Method | Endpoint                  | Description                     |
|-------------|---------------------------|---------------------------------|
| POST        | /api/employees           | Create a new employee.         |
| GET         | /api/employees           | Get all employees.             |
| GET         | /api/employees/{empId}   | Get an employee by ID.         |
| PUT         | /api/employees/{empId}   | Update an employee by ID.      |
| DELETE      | /api/employees/{empId}   | Delete an employee by ID.      |

## Request and Response Examples

### 1. Create Employee (POST /api/employees)

#### Request Body
```json
    {
          "firstName": "John",
          "lastName": "Doe",
          "email": "john.doe@example.com",
          "department": "Engineering"
    }
```
## Response (201 Created)
```declarative
    {
        "empId": 1,
        "name": "John Doe",
        "age": 30,
        "gender": "Male",
        "city": "New York",
        "pinCode": "100001"
    }
```
## 2. Get All Employees (GET /api/employees)
## Response (200 OK)
```declarative
    [
        {
            "empId": 1,
            "name": "John Doe",
            "age": 30,
            "gender": "Male",
            "city": "New York",
            "pinCode": "100001"
        }
    ]
```
## 3. Get Employee by ID (GET /api/employees/{empId})

## Response (200 OK)

```declarative
    {
        "empId": 1,
        "name": "John Doe",
        "age": 30,
        "gender": "Male",
        "city": "New York",
        "pinCode": "100001"
    }   
```
## 4. Update Employee (PUT /api/employees/{empId})
## Request Body

```declarative
    {
        "name": "Jane Smith",
        "age": 28,
        "gender": "Female",
        "city": "Los Angeles",
        "pinCode": "900001"
    }
```
## 5. Delete Employee (DELETE /api/employees/{empId})

____
## Error Responses
## 1. Validation Error (400 Bad Request)
## Request Body (Invalid Data)

```declarative
    {
        "name": "",
        "age": 17,
        "gender": "Unknown",
        "city": "NY",
        "pinCode": "12345"
    }
```

## Response

```declarative
        {
        "status": 400,
        "error": "Bad Request",
        "errors": [
            {
                "code": "VALIDATION_ERROR",
                "message": "Name is required",
                "details": "name"
            },
            {
                "code": "VALIDATION_ERROR",
                "message": "Age must be at least 18",
                "details": "age"
            },
            {
                "code": "VALIDATION_ERROR",
                "message": "Gender must be Male, Female, or Other",
                "details": "gender"
            },
            {
                "code": "VALIDATION_ERROR",
                "message": "City must be between 2 and 50 characters",
                "details": "city"
            },
            {
                "code": "VALIDATION_ERROR",
                "message": "Pin Code must be 6 digits",
                "details": "pinCode"
            }
            ]
        }
```
## 2. Resource Not Found (404 Not Found)
## Response

```declarative

    {
    "status": 404,
    "error": "Not Found",
    "errors": [
    {
        "code": "RESOURCE_NOT_FOUND",
        "message": "Employee not found with ID: 999",
        "details": null
    }
            ]
    }
```
## 3. Internal Server Error (500 Internal Server Error)
## Response

```declarative
    {
    "status": 500,
    "error": "Internal Server Error",
    "errors": [
        {
            "code": "INTERNAL_SERVER_ERROR",
            "message": "An unexpected error occurred",
            "details": "<error_message>"
        }
            ]
    }
```