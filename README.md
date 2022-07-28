# user-contact-info-service
This micro service allows clients to perform following operations
- Save user contact (User Contact + Address) into H2 DB
- Fetch user contact info by ID
- Fetch user contact for multiple IDs eg : (1,2,3)
- Fetch all user contact info
- Update user contact info by ID
- Delete user contact info by ID

# Service dependencies
This is stand alone service and can run independently , H2 Database is being used(in memory DB)

# How to clone the application from Github
`git clone https://github.com/AnilDhawaleGitHub/user-contact-service.git`

# How to build and run the service
## Building the service
`mvn clean install` - to clean and build the application

`mvn test` - to run the test cases only 

# Running the service

`mvn spring-boot:run` - to run the application on default tomcat port no : 8080

# Verify the service in swagger

http://localhost:8080/swagger-ui.html  => copy this URL and paste it in the browser

# Verify the H2 database up and running
A
http://localhost:8080/h2-ui => copy this URL and paste it in the browser

Please refer the application.properties file for the user id and password to connect H2 database

# Technologies
- [Spring cloud microservices parent v2.5.2]
- Maven 3+
- Java 11
- SpringBoot
- Junit 4
- Mock MVC
- H2 DB

# Lombok
This application uses Lombok to reduce boilerplate code.

If you use IntelliJ, please install the Lombok plugin.

*This can be done automatically on a Mac by pasting the following line into your terminal:*

*`cd ~/Library/Application\ Support/Idea*;curl -L https://plugins.jetbrains.com/plugin/download?updateId=38691 -o z;unzip z;rm z;`*

# Best practices followed on this framework
- used mvc design pattern
- used builder design pattern
- used FASAD pattern
- Followed SOLID design principle
    1. single responsibility principle 
    2. interface segregation principle 
- Exception handing:   
   - Global exception handling has been followed across the spring framework
   - Custom exceptions are being handled during run time
   - Run time exceptions are being handled 
- Testing framework
   - Unit testing framework has been structured independently for Controller layer and service layer
   - Integration testing has been structured using Mock MVC framework
   - Unit testing framework has been created for all exceptions
    