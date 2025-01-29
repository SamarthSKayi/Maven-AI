package com.example.firstfx;

import java.time.LocalDate;

public class TaskData {
    String title;
    String description;
    LocalDate dueDate;

    public TaskData(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = LocalDate.parse(dueDate);
    }

    public String getTask() {
        return "" + title + " " + dueDate + " " + description;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }
}
