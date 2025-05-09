# 🎯 KidTask – Task and Wish Management System for Children

## 👤 Developers

**Oğuzhan Tarhan** 
**Burak Aydoğmuş**  
**Nedim Göktuğ Tabak** 
Software Engineering Student  
Çankaya University – Spring 2024–2025  
Project for SENG 272 – Software Engineering Project

GitHub: [https://github.com/OgzhnTarhn/Seng272](https://github.com/OgzhnTarhn/Seng272)

## 📌 Overview

**KidTask** is a console-based Java application developed as part of the Software Engineering Project (SENG 272). It enables a child to complete tasks assigned by a parent or teacher, collect points based on performance, and redeem those points for rewards (wishes). The system gamifies daily and academic responsibilities to encourage consistency and self-discipline in children.

This repository contains:

- 📁 `src/` – Source code for all core classes
- 📄 `Commands.txt` – Command input file
- 📄 `Tasks.txt`, `Wishes.txt` – Optional initialization files
- 📝 `ProjectReport.docx` – Developer project report with all details

## 🧱 Technologies

- Java 17
- Object-Oriented Design
- Enum Types & Collections (`ArrayList`)
- Java Time API (`LocalDate`, `LocalTime`, `LocalDateTime`)
- Regex for text parsing
- File I/O via `BufferedReader` / `FileHandler`

## 📂 Folder Structure

```
KidTask/
├── src/
│   ├── com.kidtask.model/
│   │   ├── User.java (abstract)
│   │   ├── Child.java
│   │   ├── Parent.java
│   │   ├── Teacher.java
│   │   ├── Task.java
│   │   ├── Wish.java
│   │   ├── Enums/ (TaskStatus, WishType, etc.)
│   ├── com.kidtask.service/
│   │   ├── TaskService.java
│   │   ├── WishService.java
│   ├── com.kidtask.command/
│   │   ├── CommandProcessor.java
│   ├── com.kidtask.file/
│   │   ├── FileHandler.java
│   └── com.kidtask/
│       └── Main.java
├── input/
│   ├── Tasks.txt
│   ├── Wishes.txt
│   ├── Commands.txt
├── report/
│   └──ProjectReport.docx
```

## 🚀 How to Run

1. Clone the repository or download the zip.
2. Open the project in IntelliJ IDEA or any Java IDE.
3. Make sure `Commands.txt`, `Tasks.txt`, and `Wishes.txt` are in the root or appropriate `/input/` folder.
4. Run `Main.java`.
5. Console output will reflect real-time system responses.

## 📥 Command File Format (`Commands.txt`)

Each line represents one command. Examples:

```text
ADD_TASK1 F 101 "Clean Room" "Desk & Bed" 2025-05-10 10:00 5
TASK_DONE 101
TASK_CHECKED 101 4
ADD_WISH1 W301 "New Book" "Price:100TL"
WISH_CHECKED W301 APPROVED
PRINT_STATUS
```

## ✅ Features

- Task creation, completion, and approval with rating
- Wish system with point deduction and level requirement
- Point-based child leveling (1 to 4)
- Bonus point rewards by parent
- Error handling for malformed commands and invalid logic
- Full console-based interaction via input files

## 📖 Project Report

You can find the full technical documentation, testing cases, system design, class responsibilities, and developer notes inside:

📄 `report/ProjectReport.docx`

## 📜 License

This project is developed for educational purposes only.
