package com.alvi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.alvi.model.Task;

/**
 * TaskManager.java
 * ----------------------------
 * Simple in-memory manager for Task objects.
 * Acts as a service / repository that the controller calls to add/delete/manage tasks.
 *
 * Responsibilities:
 * - Maintain internal list of tasks (private encapsulation).
 * - Provide read-only copies or filtered lists to callers.
 *
 * Notes:
 * - This class is intentionally small and synchronous. For concurrency or
 *   persistence, consider thread-safety and decoupling (e.g. DAO pattern).
 */
public class TaskManager {
    // Internal storage for tasks. We expose copies to callers to preserve encapsulation.
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    /**
     * Add a task to the manager.
     *
     * @param task Task to add (should be non-null)
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Delete a task instance. Uses object equality to remove the provided task.
     *
     * @param task Task to remove
     */
    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Return a list of tasks that are marked completed.
     *
     * @return List<Task> completed tasks
     */
    public List<Task> getCompletedTasks() {
        return tasks.stream().filter(Task::isCompleted).collect(Collectors.toList());
    }

    /**
     * Return a list of tasks that are still pending (not completed).
     *
     * @return List<Task> pending tasks
     */
    public List<Task> getPendingTasks() {
        return tasks.stream().filter(t -> !t.isCompleted()).collect(Collectors.toList());
    }
}
