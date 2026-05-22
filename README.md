
---

## 8. `order-service`

**Repository About description:**  
> REST API for borrowing and returning books. Uses MySQL and communicates with Book/Member services via Feign clients.

```markdown
# Order Service

## Mandatory Information

- **Student Name**: [B.K.Harsha Nimeda Sirithunga]
- **Student Number**: [2301691058]
- **Slack Handle**: [@Harsha Nimeda]
- **GCP Project ID**: [indigo-splice-491917-q2]

## Project Description

The **Order Service** manages book borrowing orders. It allows a member to borrow a book (create an order) and return it (update order). It uses **MySQL** to persist orders and communicates with the **Book Service** and **Member Service** via OpenFeign clients to validate availability and member existence.

## Technology Stack

- Java 25
- Spring Boot 3.4.5
- Spring Data JPA (Hibernate)
- MySQL Connector
- Spring Cloud OpenFeign
- Spring Cloud Netflix Eureka Client
- Spring Cloud Config Client
- Spring Boot Actuator

## Setup / Getting Started Instructions

### Prerequisites
- Java 25
- Maven
- MySQL (running locally)
- Config Server (port 8888)
- Eureka Server (port 8761)
- Book Service and Member Service must be running

### Database Setup

Create the database:

```sql
CREATE DATABASE order_db;
