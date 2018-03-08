package com.github.reallive.idea.timetrack.toggl.model;

public class TogglTimeDTO {
    private TogglTime time_entry;
    private TogglTime data;

    public TogglTimeDTO() {
    }

    public TogglTimeDTO(TogglTime time_entry) {
        this.time_entry = time_entry;
    }

    public TogglTime unwrap() {
        return time_entry == null ? data : time_entry;
    }

    public TogglTime getTime_entry() {
        return time_entry;
    }

    public void setTime_entry(TogglTime time_entry) {
        this.time_entry = time_entry;
    }

    public TogglTime getData() {
        return data;
    }

    public void setData(TogglTime data) {
        this.data = data;
    }
}
