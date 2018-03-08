package com.github.reallive.idea.timetrack.toggl.model;

import com.google.gson.annotations.SerializedName;

public class TogglUser {

    @SerializedName("default_wid")
    private int workspace;

    @SerializedName("api_token")
    private String apiToken;

    public int getWorkspace() {
        return workspace;
    }

    public void setWorkspace(int workspace) {
        this.workspace = workspace;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

}
