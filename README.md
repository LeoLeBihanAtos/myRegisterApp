# User Registration API

## What is the Project?

This project is a simple Spring Boot API that exposes two services for managing user information. The API allows you to register a user and retrieve the details of a registered user. The user model is defined with the following attributes:

- **Username** (required)
- **Birthdate** (required)
- **Country of residence** (required)

Optional user attributes include:
- **Phone number**
- **Gender**

The API exposes two endpoints:
1. **POST /users** - Register a new user.
2. **GET /users/{id}** - Retrieve the details of a registered user by their ID.

To describe and document the API, Swagger has been integrated into the project, providing a UI for easy interaction with the endpoints.

## How to Run the Project / Build from Source

### Prerequisites

Before you run the project, make sure you have the following installed on your machine:

- **Java 21 or higher**
- **Maven**
- **Docker** (optional, for running with Docker Compose)

### Running the Application Using Docker Compose

The easiest way to run this project is using Docker Compose, which will automatically build and start the application in a container. To run the project, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/LeoLeBihanAtos/myRegisterApp.git
   cd myRegisterApp
   ```

2. **Run the application**:
   ```bash
   docker compose up -d
   ```

This will build the Docker image (if not already built) and start the application. The API will be available at [http://localhost:8080/api](http://localhost:8080/api).

### Running the Application Using Maven

If you prefer to run the application without Docker, you can build and run it with Maven. Here's how:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/LeoLeBihanAtos/myRegisterApp.git
   cd myRegisterApp
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

This will start the application on the default port `8080`.

## How to Use the API

Once the application is running, you can take a look at Swagger or interact via HTTP requests.

### Swagger UI

Swagger is integrated into the project to provide an interactive UI for testing the API. You can access Swagger at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

From there, you can test both the **POST /users/register** and **GET /users/{id}** endpoints interactively.

### API Endpoints

1. **POST /users/registers**
    - **Description**: Register a new user.
    - **Request Body**:
      ```json
      {
        "username": "jamespotter",
        "birthdate": "1990-01-01",
        "countryOfResidence": "USA",
        "phoneNumber": "+33658595253",
        "gender": "Male"
      }
      ```
    - **Response**:
        - `201 Created` with the user details and the generated user ID.

2. **GET /users/{id}**
    - **Description**: Retrieve the details of a registered user by their ID.
    - **Path Parameters**:
        - `id`: The ID of the user to retrieve.
    - **Response**:
        - `200 OK` with the user details:
          ```json
          {
            "id": 1,
            "username": "jamespotter",
            "birthdate": "1990-01-01",
            "countryOfResidence": "USA",
            "phoneNumber": "+33658595253",
            "gender": "Male"
          }
          ```

### Postman Collection

1. Download the [Postman collection](./User_Registration_API.postman_collection.json).
2. Import the collection into Postman:
    - Open Postman.
    - Click on "Import" in the top-left corner.
    - Select the file `User_Registration_API.postman_collection.json`.
3. The collection will now appear in Postman, and you can start testing the API by sending requests directly.

The collection includes the following requests:
- **POST /api/users/register**: Register a new user.
- **GET /api/users/{id}**: Retrieve the details of a registered user by their ID.

### Example Using Curl

1. **Register a User (POST /users)**:
   ```bash
   curl -X POST "http://localhost:8080/api/users" -H "Content-Type: application/json" -d '{
     "username": "jamespotter",
     "birthdate": "1990-01-01",
     "countryOfResidence": "USA",
     "phoneNumber": "+33658595253",
     "gender": "Male"
   }'
   ```

2. **Get User Details (GET /users/{id})**:
   ```bash
   curl "http://localhost:8080/api/users/1"
   ```

## Conclusion

This project provides a simple and efficient way to manage user information with a Spring Boot backend and an integrated Swagger UI. The API can be easily run using Docker Compose or Maven, and the Swagger UI provides an intuitive interface for testing the endpoints.

For more information, feel free to check the repository or contact the maintainers.

   