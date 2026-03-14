package com.ketchup.todos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class TodoRequest {

    @NotEmpty(message = "Tittle is mandatory")
    @Size(min = 3, max = 30, message = "Tittle must be between 3 and 30 characters")
    private String tittle;

    @NotEmpty(message = "Description is mandatory")
    @Size(min = 3, max = 250, message = "Description must be between 3 and 250 characters")
    private String description;

    @Min(1)
    @Max(5)
    private int priority;

    public TodoRequest(String tittle, String description, int priority) {
        this.tittle = tittle;
        this.description = description;
        this.priority = priority;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
