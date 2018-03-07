package rip.faith_in_humanity.time.toggl.model;

public class TogglSession {
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
