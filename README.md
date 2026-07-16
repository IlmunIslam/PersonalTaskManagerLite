# Personal Task Manager (Lite)

A small JavaFX desktop application for managing a personal to-do list. Tasks are
created with a title and due date, tracked as pending or completed, and persisted
to disk between runs.

## Features

- **Create tasks** with a title and a due date (via a date picker), with basic
  validation that rejects empty titles or missing dates.
- **Due-date sorting** — the list is kept ordered by due date as tasks are added.
- **Pending / completed tracking** — mark a selected task as complete; completed
  tasks are shown in green italic with a check mark, pending tasks in plain text.
- **Filter the view** — toggle between *Show All* and *Show Completed*.
- **Delete tasks** from the list.
- **Automatic persistence** — tasks are saved to a plain-text file (`alvi.txt`) in
  the working directory on every change and reloaded on startup. The format is a
  human-readable pipe-separated line per task: `title|yyyy-MM-dd|completed`.

## Tech stack

- **Java 17**
- **JavaFX 21.0.3** (`javafx-controls`, `javafx-fxml`) — UI defined in FXML
  (`main_view.fxml`) with a CSS stylesheet (`style.css`)
- **Maven** — build and dependency management (`javafx-maven-plugin`)

## Prerequisites

- JDK 17 or newer
- Maven 3.6+ (no Maven wrapper is bundled; use a system `mvn`)

## Build and run

From the project root:

```bash
# Compile
mvn compile

# Run the application
mvn javafx:run
```

To produce a packaged jar:

```bash
mvn package
```

The main class is `com.alvi.MainApp`.

## Project origin

This project began as a university Object-Oriented Programming (OOP) coursework
assignment and has since been cleaned up and maintained as a standalone repository.
