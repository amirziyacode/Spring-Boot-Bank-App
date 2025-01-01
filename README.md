# Simple-Bank
![bank-01-Converted-01-1024x428](https://github.com/user-attachments/assets/02e048d0-25ca-43b6-a0e9-4742c55dcc19)
[![GitHub stars](https://img.shields.io/github/stars/iampawan/FlutterExampleApps.svg?style=social&label=Star)](https://github.com/amirziyacode)
[![GitHub forks](https://img.shields.io/github/forks/iampawan/FlutterExampleApps.svg?style=social&label=Fork)](https://github.com/amirziyacode?tab=repositories)


# BankUser Table

This document provides an overview of the `BankUser` table, its structure, and usage within the application.

---

## Table Name
**BankUser**

---

## Table Structure

| Column Name   | Data Type      | Constraints                        | Description                            |
|---------------|----------------|-------------------------------------|----------------------------------------|
| `id`          | `INTEGER`      | Primary Key, Auto-Increment         | Unique identifier for each user.       |
| `username`    | `VARCHAR`      | Not Null, Not Blank                | The username of the user.              |
| `password`    | `VARCHAR`      | Not Null, Not Blank                | The password of the user.              |
| `amount`      | `DOUBLE`       | None                               | The account balance of the user.       |
| `accountNumber` | `UUID`       | None                               | The unique account number for the user.|

---

## Table Constraints

- **Primary Key**: `id`
- **Validation**:
  - `username`: Cannot be blank or null.
  - `password`: Cannot be blank or null.

---

## Relationships
- Currently, no foreign key relationships are defined for the `BankUser` table.

---

## Sample Data

| id | username   | password     | amount | accountNumber                         |
|----|------------|--------------|--------|---------------------------------------|
| 1  | johndoe    | pass1234     | 1500.50 | `c1a1b1c1-1234-5678-9101-1a2b3c4d5e6f` |
| 2  | janedoe    | securepass   | 2500.00 | `d2e2f2g2-2234-5678-9101-2b3c4d5e6f7g` |

---

## Entity Definition (Java)

```java
@Entity(name = "BankUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "UserName Should Not Be Blank !!")
    @NotNull
    private String username;

    @NotBlank(message = "Password Should Not Be Blank !!")
    @NotNull
    private String password;

    private double amount;

    private UUID accountNumber;
}
```

---

## Usage

This table stores information about users in the banking application. It includes details like:
- Login credentials (`username`, `password`).
- Account balance (`amount`).
- Unique identifiers (`id`, `accountNumber`).
