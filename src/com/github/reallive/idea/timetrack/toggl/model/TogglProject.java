package com.github.reallive.idea.timetrack.toggl.model;

import com.google.gson.annotations.SerializedName;

public class TogglProject {
    private Long id;
    @SerializedName("wid")
    private Long workspace;
    private String name;

    public TogglProject(Long workspace, String name) {
        this.workspace = workspace;
        this.name = name;
    }

    public TogglProject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Long workspace) {
        this.workspace = workspace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
