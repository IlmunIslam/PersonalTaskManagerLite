package com.alvi;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.alvi.model.Task;

/**
 * FileHelper.java
 * ----------------------------
 * Simple utility class to persist Task objects to a plain text file and to
 * load them back. Uses a very small, human-readable pipe-separated format:
 *
 *   title|yyyy-MM-dd|completed
 *
 * Example:
 *   Finish report|2025-10-27|false
 *
 * Notes / Design decisions:
 * - File is stored relative to the working directory as "tasks.txt".
 * - The format is intentionally simple so instructor can read the file.
 * - All IO exceptions are caught and printed (no throws) so UI won't crash.
 * - If you want stronger guarantees (atomic writes), consider writing to a
 *   temporary file and renaming it.
 */
public class FileHelper {
    private static final String FILE_PATH = "alvi.txt";

    /**
     * Save the provided list of tasks to disk.
     * Overwrites the file each time.
     *
     * @param tasks list of Task objects to persist
     */
    public static void saveTasks(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.write(task.getTitle() + "|" + task.getDueDate() + "|" + task.isCompleted());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all tasks from file
    public static List<Task> loadTasks() {
        List<Task> loadedTasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return loadedTasks;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String title = parts[0];
                    LocalDate date = LocalDate.parse(parts[1]);
                    boolean completed = Boolean.parseBoolean(parts[2]);
                    Task task = new Task(title, date);
                    task.setCompleted(completed);
                    loadedTasks.add(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedTasks;
    }
}
