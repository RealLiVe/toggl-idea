package com.github.reallive.idea.timetrack.toggl.model;

import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class TogglTime {
    @SerializedName("pid")
    private Long project;

    @SerializedName("id")
    private Long id;

    @SerializedName("wid")
    private Integer workspaceId;

    @SerializedName("start")
    private ZonedDateTime start;

    @SerializedName("stop")
    private ZonedDateTime end;

    @SerializedName("duration")
    private Long duration;

    @SerializedName("description")
    private String description;

    @SerializedName("tags")
    private List<String> tags = new LinkedList<>();

    @SerializedName("created_with")
    private String createdWith = "toggl-idea";

    public TogglTime(int workspaceId, ZonedDateTime start, ZonedDateTime end, String description) {
        this.workspaceId = workspaceId <= 0 ? null : workspaceId;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public TogglTime(int workspaceId, String description) {
        this.workspaceId = workspaceId;
        this.description = description;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatedWith() {
        return createdWith;
    }

    public void setCreatedWith(String createdWith) {
        this.createdWith = createdWith;
    }

    @Override
    public String toString() {
        return "TogglTime{" +
                "description='" + description + '\'' +
                '}';
    }

    public Long getProject() {
        return project;
    }

    public TogglTime setProject(Long project) {
        this.project = project;
        return this;
    }
}
