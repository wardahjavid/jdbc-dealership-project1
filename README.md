# Project Title  
**JDBC Dealership Management System**

-------------------------------------------------------------------------------------

## Description of the Project  
This Java console application simulates a complete vehicle dealership management system.  
It connects to a MySQL database using JDBC and allows users to:
- Search for vehicles based on multiple criteria  
- Add new vehicles into the dealership inventory  
- Create contracts (Sales or Lease) for existing vehicles  
- Remove vehicles from inventory  
- Store and retrieve database information using DAO and SQL  

The purpose of this project is to demonstrate database operations, console UI development, and Java–SQL integration as part of **Workbook 8C**.  
The intended users are students, instructors, and anyone learning Java JDBC application development.
The application solves the problem of managing dealership data by providing a simple, menu-driven interface that interacts with a real relational database.

-------------------------------------------------------------------------------------

## User Stories  

- **As a user, I want to be able to input vehicle information, so that the system can store it in the database.**  
- **As a user, I want to search for vehicles using different filters, so that I can quickly find the car I'm looking for.**  
- **As a user, I want to receive immediate feedback after each action, so that I know whether the operation succeeded.**  
- **As a user, I want to generate contracts (sales or lease), so that the dealership can record finalized agreements.**  
- **As a user, I want to remove vehicles that are no longer in inventory, so the database remains accurate.**

---

## Setup  

### Instructions on How to Set Up and Run the Project Using IntelliJ IDEA

Follow the instructions below to set up and run this project.

---

## Prerequisites  

- **IntelliJ IDEA**  
  Ensure IntelliJ IDEA is installed. You can download it here:  
  https://www.jetbrains.com/idea/

- **Java SDK (JDK 17 or higher)**  
  Make sure Java SDK is installed and configured inside IntelliJ.

- **MySQL Server 8.x**  
  The project uses a MySQL database named `car_dealership`.  
  You must run the SQL schema provided in the project folder.

---

## Running the Application in IntelliJ  

1. Open **IntelliJ IDEA**  
2. Click **Open** and navigate to the project directory  
3. Let IntelliJ finish indexing and importing libraries  
4. Navigate to:  
   `src/main/java/com/yearup/dealership/Main/Main.java`
5. Right-click `Main.java`  
6. Select **Run 'Main.main()'**  
7. The application menu will appear in the console

---

## Technologies Used  

- **Java 17** – Core implementation language  
- **JDBC** – Database connectivity  
- **MySQL 8.x** – Relational database  
- **Apache DBCP2** – DataSource connection pooling  
- **Maven** – Dependency and build management  
Dependencies (from pom.xml):

