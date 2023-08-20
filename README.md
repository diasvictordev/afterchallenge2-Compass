<p align="center">
<img src="https://carreiras.pucminas.br/med/2022/09/logo_04.png" height="200" width="200">
</p>

# Compass Scholarship Program API

The Compass Scholarship Program API is a RESTful API developed using the Spring Boot framework in Java. The API is capable of handling basic operations such as GET, POST, PUT, and DELETE. It provides endpoints to manage the registration of organizers, students, classes, and squads for the Compass Scholarship Program.

## Technologies Used
- Java
- Spring Boot
- MySQL
- JUnit
- Mockito
- RESTful API principles

## Setup Instructions

1. Clone the repository to your local machine.

2. Make the necessary configurations in the application.properties file (database-username and database-password).

3. Build the project using Maven:

`mvn clean install`  

4. Run the application:

`mvn spring-boot:run`

The API will start running on `http://localhost:8080`.

## API Endpoints

### Organizers
- `POST /api/organizer`: Create a new organizer.
- `GET /api/organizer`: Get all organizers.
- `GET /api/organizer/{organizerId}`: Get details of a specific organizer by ID.
- `PUT /api/organizer`: Update an existing organizer.
- `DELETE /api/organizer/{organizerId}`: Delete an organizer.

### Students

- `POST /api/student`: Create a new student.
- `GET /api/student`: Get all students.
- `GET /api/student/{studentId}`: Get details of a specific student by ID.
- `PUT /api/student`: Update an existing student.
- `DELETE /api/student/{studentId}`: Delete a student.

### Classes

- `POST /api/class`: Create a new class.
- `GET /api/class`: Get all classes.
- `GET /api/class/{classId}`: Get details of a specific class by ID.
- `PUT /api/class`: Update an existing class.
- `DELETE /api/class/{classId}`: Delete a class.

### Squads

- `POST /api/squad`: Create a new squad.
- `GET /api/squad`: Get all squads.
- `GET /api/squad/{squadId}`: Get details of a specific squad by ID.
- `PUT /api/squad`: Update an existing squad.
- `DELETE /api/squad/{squadId}`: Delete a squad.

## Error Handling

The API provides appropriate error responses for invalid requests or server errors. Custom exception handlers are implemented to ensure error messages are informative and user-friendly.

## Data Validation
The password must contain at least an uppercase letter, a lowercase letter, a number and an especial character.
The role of Organizer must be "ScrumMaster", "Coordinator" or "Instructor".


## Conclusion

The Compass Scholarship Program API provides a flexible and efficient way to manage the registration of organizers, students, classes, and squads. It is built on the powerful Spring Boot framework and can be easily integrated with MySQL.

Feel free to explore the API endpoints and start using it for your Compass Scholarship Program management needs! 

Happy coding!
