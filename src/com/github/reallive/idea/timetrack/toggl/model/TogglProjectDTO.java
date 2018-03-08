package com.github.reallive.idea.timetrack.toggl.model;

public class TogglProjectDTO {
    private TogglProject project;
    private TogglProject data;

    public TogglProjectDTO() {
    }

    public TogglProjectDTO(TogglProject project) {
        this.project = project;
    }

    public TogglProject getProject() {
        return project;
    }

    public TogglProject getData() {
        return data;
    }

    public TogglProject unwrap() {
        return project == null ? data : project;
    }
}
