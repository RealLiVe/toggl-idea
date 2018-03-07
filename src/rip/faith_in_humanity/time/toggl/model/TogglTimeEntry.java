package rip.faith_in_humanity.time.toggl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.xml.internal.ws.developer.Serialization;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class TogglTimeEntry {
    @SerializedName("id")
    protected long id;

    @SerializedName("wid")
    protected int workspaceId;

    @SerializedName("start")
    @Expose(serialize = false, deserialize = true)
    protected ZonedDateTime start;

    @SerializedName("stop")
    @Expose(serialize = false, deserialize = true)
    protected ZonedDateTime end;

    @SerializedName("duration")
    protected long duration;

    @SerializedName("description")
    protected String description;

    @SerializedName("tags")
    protected List<String> tags = new LinkedList<>();

    @SerializedName("created_with")
    protected String createdWith = "toggl-idea";
    public TogglTimeEntry(int workspaceId, ZonedDateTime start, ZonedDateTime end, String description) {
        this.workspaceId = workspaceId;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public TogglTimeEntry(int workspaceId, String description) {
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
}
