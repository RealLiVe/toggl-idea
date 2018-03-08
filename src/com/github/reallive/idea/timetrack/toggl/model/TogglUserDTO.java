package com.github.reallive.idea.timetrack.toggl.model;

public class TogglUserDTO {
    private long since;
    private TogglUser data;

    public long getSince() {
        return since;
    }

    public void setSince(long since) {
        this.since = since;
    }

    public TogglUser getData() {
        return data;
    }

    public void setData(TogglUser data) {
        this.data = data;
    }
}
