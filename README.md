# Financial Data Visualization System

A full-stack web application for uploading, managing, and visualizing financial records through Excel file uploads. Built with Spring Boot backend and Angular frontend.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [TODO/Enhancemnets](#todo--enhancements)
- [Database Schema](#database-schema)
- [Contributing](#contributing)

## Overview

This application allows users to upload Excel files containing financial data, automatically processes the data, stores it in a database, and provides interactive visualizations. Users can view their financial records in both tabular and chart formats.

## Features

- **Excel File Upload**: Upload financial data via Excel (.xlsx) files
- **Data Validation**: Robust validation for user inputs and file formats
- **Data Visualization**: Interactive bar charts using Chart.js
- **User Management**: Multi-user support with user-specific data isolation
- **Year-based Organization**: Financial records organized by year
- **Data Replacement**: Automatic replacement of existing records for the same user/year
- **Responsive UI**: Clean, responsive interface for data management

## Tech Stack

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Apache POI** (Excel processing)
- **MySQL/H2 Database**
- **Maven** (Build tool)

### Frontend
- **Angular 15+**
- **TypeScript**
- **Chart.js** (Data visualization)
- **Bootstrap/CSS** (Styling)
- **HttpClient** (API communication)

## Project Structure

```
assessments/
├── FinancialDataBackend/                 # Spring Boot Backend
│   ├── src/main/java/com/ThatoMoet/
│   │   ├── controller/
│   │   │   └── MyRestController.java     # REST API endpoints
│   │   ├── entities/
│   │   │   ├── FinancialRecords.java     # Financial record entity
│   │   │   └── User.java                 # User entity
│   │   ├── repository/
│   │   │   ├── FinancialDataRepository.java
│   │   │   └── UserRepository.java
│   │   └── FinancialDataVisualisationApplication.java
│   ├── pom.xml                          # Maven dependencies
│   └── mvnw, mvnw.cmd                   # Maven wrapper
│
└── FinancialDataUI/                     # Angular Frontend
    ├── src/app/
    │   ├── app.component.html           # Main UI template
    │   ├── app.component.ts             # Component logic
    │   ├── app.component.css            # Component styles
    │   └── app.module.ts                # App module configuration
    ├── package.json                     # npm dependencies
    └── angular.json                     # Angular configuration
```

## Prerequisites

- **Java 17** or higher
- **Node.js 18** or higher
- **npm** or **yarn**
- **Maven 3.6** or higher
- **MySQL** (optional - H2 can be used for development)

## Installation & Setup

### Backend Setup

1. **Navigate to backend directory:**
   ```bash
   cd FinancialDataBackend
   ```

2. **Install dependencies and run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory:**
   ```bash
   cd FinancialDataUI
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start the development server:**
   ```bash
   ng serve
   ```

   The frontend will start on `http://localhost:4200`

### Database Configuration

By default, the application uses H2 in-memory database. To use MySQL:

1. Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/financial_data
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

## Usage

1. **Start both backend and frontend servers**
2. **Open browser to `http://localhost:4200`**
3. **Enter User ID and Year**
4. **Select Excel file** (.xlsx format with Month and Amount columns)
5. **Click Upload** to process the file
6. **View data** in table format and interactive chart

### Excel File Format
Your Excel file should have the following structure:
```
| Month    | Amount  |
|----------|---------|
| January  | 5000.00 |
| February | 4500.50 |
| March    | 6200.25 |
```

## API Endpoints

### Upload Financial Data
```http
POST /api/finances/upload/{userId}/{year}
Content-Type: multipart/form-data

Parameters:
- userId (path): User ID
- year (path): Year for the financial data
- excelFile (form-data): Excel file to upload
```

### Get Financial Records
```http
GET /api/finances/{userId}/{year}

Parameters:
- userId (path): User ID
- year (path): Year to retrieve data for

Response: List of FinancialRecords
```

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);
```

### Financial Records Table
```sql
CREATE TABLE financial_records (
    record_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    year INT NOT NULL,
    month VARCHAR(255) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

## Development Notes

- **CORS**: Backend configured to accept requests from `http://localhost:4200`
- **File Validation**: Only .xlsx files are accepted
- **Data Replacement**: Uploading data for existing user/year combination replaces old records
- **Error Handling**: Comprehensive error handling for file processing and API calls
  
# TODO / Enhancements

## Backend Improvements
- [ ] Add user authentication and authorization (JWT tokens)
- [ ] Implement user registration and login endpoints
- [ ] Add input validation for Excel file structure

## Frontend Enhancements
- [ ] Implement user authentication UI (login/signup forms)
- [ ] Implement multiple chart types (line, pie, scatter)
- [ ] Implement responsive design improvements
- [ ] Create dashboard with summary statistics
- [ ] Implement drag-and-drop file upload
- [ ] Add progress indicators for file processing

## Technical Improvements
- [ ] Add comprehensive unit and integration tests
- [ ] Implement proper error handling and user feedback
- [ ] Set up CI/CD pipeline (GitHub Actions)
- [ ] Add Docker containerization
- [ ] Implement database migrations

## Security Enhancements
- [ ] Add HTTPS configuration
- [ ] Add API key authentication option
- [ ] Implement secure file upload validation
- [ ] Add password encryption for user accounts

## Performance Optimizations
- [ ] Implement lazy loading for large datasets
- [ ] Optimize bundle size for frontend

## User Experience
- [ ] Add tooltips and help documentation
- [ ] Add dark/light theme toggle
- [ ] Implement accessibility features 
- [ ] Add multi-language support (i18n)

## License

This project is licensed under the MIT License.

## Troubleshooting

### Common Issues:

**Backend won't start:**
- Check if Java 17+ is installed: `java --version`
- Verify Maven installation: `mvn --version`
- Check if port 8080 is available

**Frontend won't start:**
- Check Node.js version: `node --version`
- Clear npm cache: `npm cache clean --force`
- Delete node_modules and reinstall: `rm -rf node_modules && npm install`

**File upload fails:**
- Ensure Excel file has correct format (Month, Amount columns)
- Check file size limits
- Verify backend is running and accessible

---

**Author:** ThatoMoet  
**Version:** 1.0.0  
**Last Updated:** September 2025