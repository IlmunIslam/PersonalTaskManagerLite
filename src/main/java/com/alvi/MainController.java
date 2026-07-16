package com.alvi;

import com.alvi.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

/**
 * MainController.java
 * --------------------------------
 * This class acts as the controller for the main_view.fxml layout.
 * It handles all user interactions with the interface.
 *
 * Responsibilities:
 * - Manage adding, editing, deleting, and marking tasks as done.
 * - Update the task list view dynamically.
 * - Connect UI elements (FXML components) with the backend logic.
 */

public class MainController {

    @FXML private TextField taskTitleField;
    @FXML private DatePicker dueDatePicker;
    @FXML private ListView<Task> taskListView;

    private TaskManager taskManager;
    private ObservableList<Task> taskObservableList;

    /**
     * Initializes the controller and sets up the ListView.
     * Called automatically after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        taskManager = new TaskManager();

        // Load previously saved tasks
        List<Task> loaded = FileHelper.loadTasks();
        for (Task t : loaded) {
            taskManager.addTask(t);
        }

        taskObservableList = FXCollections.observableArrayList(taskManager.getAllTasks());
        taskListView.setItems(taskObservableList);

        //  Custom ListView cell to show completed tasks differently
        taskListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setStyle("");
                } else {
                    String display = task.getTitle() + " (Due: " + task.getDueDate() + ")";
                    if (task.isCompleted()) {
                        setText("✅ " + task.getTitle() + " (Completed, was due: " + task.getDueDate() + ")");
                        setStyle("-fx-text-fill: green; -fx-font-style: italic;");
                    } else {
                        setText(task.getTitle() + " (Due: " + task.getDueDate() + ")");
                        setStyle("-fx-text-fill: black;");
                    }

                }
            }
        });
    }

    /**
     * Adds a new task to the list when the "Add" button is clicked.
     */
    @FXML
    private void onAddTask() {
        String title = taskTitleField.getText();
        LocalDate date = dueDatePicker.getValue();

        if (title == null || title.trim().isEmpty() || date == null) {
            showAlert("Please enter your task name & due date.");
            return;
        }
        taskObservableList.sort((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()));


        Task newTask = new Task(title.trim(), date);
        taskManager.addTask(newTask);
        taskObservableList.add(newTask);

        // persist
        FileHelper.saveTasks(taskManager.getAllTasks());

        taskTitleField.clear();
        dueDatePicker.setValue(null);
    }

    /**
     * Marks the selected task as completed.
     */
    @FXML
    private void onMarkComplete() {
        Task selected = taskListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.markAsCompleted();
            taskListView.refresh();
            FileHelper.saveTasks(taskManager.getAllTasks());
        } else {
            showAlert("Select a task to mark as complete.");
        }
    }

    /**
     * Deletes the selected task from the list.
     */
    @FXML
    private void onDeleteTask() {
        Task selected = taskListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            taskManager.deleteTask(selected);
            taskObservableList.remove(selected);
            FileHelper.saveTasks(taskManager.getAllTasks());
        } else {
            showAlert("Select a task to delete.");
        }
    }

    @FXML
    private void onShowCompleted() {
        taskObservableList.setAll(taskManager.getCompletedTasks());
    }

    @FXML
    private void onShowAll() {
        taskObservableList.setAll(taskManager.getAllTasks());
    }

    /**
     * Shows a simple alert message for validation or error cases.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
