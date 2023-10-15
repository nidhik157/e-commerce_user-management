
# User Management Spring Boot Project

This is a Spring Boot project for user management in Ecommerce project.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Setup](#project-setup)
- [Running the Project](#running-the-project)
- [Local Server URL](#local-server-url)
- [Swagger Documentation](#swagger-documentation)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 8 or higher installed.
- Maven build tool installed.
- A code editor or IDE of your choice (e.g., IntelliJ IDEA, Eclipse).
- Git for version control (optional).

## Project Setup

1. Clone the repository (if you have not already done so):

   ```bash
   git clone https://github.com/nidhik157/e-commerce_user-management.git
   ```

2. Open the project in your preferred code editor or IDE.

3. Make any necessary configuration changes (e.g., database configuration) in the `application.properties`.
```spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql:{dbUrl}
spring.datasource.username={dbusername}
spring.datasource.password={dbpassword}
spring.datasource.driver-class-name =com.mysql.cj.jdbc.Driver
spring.jpa.show-sql= true
server.port=5000
spring.profiles.active=dev
spring.security.user.password={secuserpassword}
management.endpoints.web.base-path=/admin
management.endpoints.web.exposure.include=health,info,metrics
#springdoc.show-actuator=true
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username={email}
spring.mail.password={email app password}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```


4. Build the project using Maven:

   ```bash
   mvn clean install
   ```

## Running the Project

To run the Spring Boot application locally, follow these steps:

1. Open a terminal and navigate to the project directory.

2. Run the following command:

   ```bash
   java -jar target/user-management-1.0.jar
   ```

   Replace `user-management-1.0.jar` with the actual name of the generated JAR file.

3. The application will start, and you will see logs indicating that the server is running.

## Local Server URL

Once the application is running locally, you can access it at the following URL:

- [http://localhost:5000](http://localhost:5000)

## Swagger Documentation

The API documentation for this project is available through Swagger. To access the Swagger UI, use the following URL:

- [http://localhost:5000/swagger-ui/index.html#/](http://localhost:5000/swagger-ui/index.html#/)

Here, you can explore and test the API endpoints.

## Contributors

- Kumari Nidhi <knidhi2196@gmail.com>

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

Happy coding!
```
