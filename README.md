# swissre-exercise106

## Overview

The Swiss Re exercise 106 involves analyzing the organizational structure of BIG COMPANY to ensure fair salary practices and identify reporting line issues. The program evaluates the salaries of managers in relation to their direct subordinates and checks for excessive reporting lines within the company.

## Problem Statement

BIG COMPANY aims to:

1. Ensure every manager earns at least 20% more and no more than 50% more than the average salary of their direct subordinates.
2. Identify employees with more than 4 managers between them and the CEO.

### Input

A CSV file containing employee information, structured as follows:

- Each line represents an employee (including the CEO).
- The CEO has no manager specified.
- Up to 1000 rows of employee data.

## Requirements

- **Programming Language**: Java SE (any version)
- **Testing Framework**: JUnit (any version)
- **Build Tool**: Maven
- **Output**: Console output, no GUIs

## Tasks

The program must perform the following:

- Identify managers who earn less than they should, including the amount by which they are underpaid.
- Identify managers who earn more than they should, including the amount by which they are overpaid.
- Identify employees with a reporting line longer than 4 levels, including the length of the reporting line.

## Getting Started

### Prerequisites

- Java SE (version 8 or higher)
- Maven
- JUnit

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/swissre-exercise106.git

2. Navigate to the project directory:
   ```bash
   cd swissre-exercise106

3. Build the project:
   ```bash 
   mvn clean install
   
4. Run the application with the input CSV file **(Note: csv file is optional if not provided default csv file will be read)**:
   ```bash 
   java -jar target/swissre-exercise106.jar path/to/employees.csv (with csv)
   java -jar target/swissre-exercise106.jar (without csv)
   
5. Run the tests using Maven:
   ```bash 
   mvn test   
   

   
