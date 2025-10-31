# EmployeeDatabaseApp (Student-style)

Simple menu-driven Java JDBC CRUD app for an `employees` table.

**Files**
- `EmployeeDatabaseApp.java` - main Java file
- `setup.sql` - SQL script to create database/table
- `Interview_QA.pdf` - common JDBC interview Q&A (or fallback .txt if PDF not available)
- `screenshots/` - example console screenshots

## DB setup (MySQL)
1. Start MySQL server.
2. Run `setup.sql` (it creates database `college` and `employees` table).
3. The Java app uses:
```
URL = "jdbc:mysql://localhost:3306/college"
user = root
password = (empty)
```

## setup.sql
```sql
CREATE DATABASE IF NOT EXISTS college;
USE college;

CREATE TABLE IF NOT EXISTS employees (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  role VARCHAR(100),
  salary DOUBLE
);
```

## How to compile & run
1. Add MySQL JDBC driver (mysql-connector-java) to classpath.
2. Compile:
   ```bash
   javac -cp .:mysql-connector-java.jar EmployeeDatabaseApp.java
   ```
   (On Windows use `;` instead of `:`)
3. Run:
   ```bash
   java -cp .:mysql-connector-java.jar EmployeeDatabaseApp
   ```

## Student notes
- Code is simple and straightforward for learning JDBC basics.
- Uses `PreparedStatement` to avoid SQL injection.

## Example console screenshots
- `screenshots/menu.png`
- `screenshots/after_add.png`

