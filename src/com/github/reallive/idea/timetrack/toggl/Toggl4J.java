package com.github.reallive.idea.timetrack.toggl;

import com.github.reallive.idea.timetrack.toggl.model.*;
import com.github.reallive.idea.timetrack.toggl.utils.Response;
import com.github.reallive.idea.timetrack.toggl.utils.SimpleIOTemplate;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class Toggl4J {

    private static final String TOGGL_ENDPOINT = "https://www.toggl.com/api/v8";

    private String apiToken;

    private int workspaceId;

    private boolean connected = false;

    private Pair<TogglTime, Long> timeoutPair = Pair.of(null, Long.MIN_VALUE);

    private ConcurrentHashMap<String, Long> projectMap = new ConcurrentHashMap<>();

    private long lastProjectSync = Long.MIN_VALUE;

    public synchronized TogglTime startEntry(String project, String description) {
        if(connected) {
            refresh(false);
            if (!projectMap.containsKey(project)) {
                createProject(project);
            }
            Long id = projectMap.get(project);
            Response<TogglTimeDTO> object = new SimpleIOTemplate(TOGGL_ENDPOINT
                    + "/time_entries/start", SimpleIOTemplate.RequestMethod.POST)
                    .addAuth(apiToken, "api_token")
                    .send(new TogglTimeDTO(new TogglTime(workspaceId, description).setProject(id)), TogglTimeDTO.class);
            if (object.getCode() != 200) {
                return null;
            } else {
                timeoutPair = Pair.of(object.getBody().unwrap(), System.currentTimeMillis());
                return object.getBody().unwrap();
            }
        }
        return null;
    }

    public synchronized Toggl4J stop() {
        this.refresh(true);
        if (timeoutPair != null && timeoutPair.getLeft() != null) {
            stop(timeoutPair.getLeft().getId());
        }
        return this;
    }

    public synchronized Toggl4J stop(long id) {
        Response<TogglTimeDTO> object2 = new SimpleIOTemplate(TOGGL_ENDPOINT + "/time_entries/" + id + "/stop",
                SimpleIOTemplate.RequestMethod.PUT)
                .addAuth(apiToken, "api_token")
                .send(TogglTimeDTO.class);

        if (object2.getCode() == 200) {
            timeoutPair = Pair.of(null, System.currentTimeMillis());
        }
        return this;
    }

    public synchronized TogglTime get() {
        refresh(false);
        return timeoutPair.getLeft();
    }

    public Toggl4J() {
        this.connected = false;
    }

    public synchronized boolean connect(String apiToken) {
        this.connected = false;
        try (SimpleIOTemplate template = new SimpleIOTemplate(TOGGL_ENDPOINT + "/me",
                SimpleIOTemplate.RequestMethod.GET)) {
            template.addAuth(apiToken, "api_token");
            Response<TogglUserDTO> object = template.send(TogglUserDTO.class);
            if (object.getCode() == 200) {
                this.apiToken = apiToken;
                connected = true;
                workspaceId = object.getBody().getData().getWorkspace();
                refresh(true);
            }
        } catch (IOException e) {
            connected = false;
        }
        return connected;
    }

    private synchronized void refresh(boolean force) {
        if (connected) {
            if (timeoutPair == null || timeoutPair.getRight() < System.currentTimeMillis() - 10000 || force) {
                Response<TogglTimeDTO> object2 = new SimpleIOTemplate(TOGGL_ENDPOINT + "/time_entries/current",
                        SimpleIOTemplate.RequestMethod.GET)
                        .addAuth(apiToken, "api_token")
                        .send(TogglTimeDTO.class);

                TogglTime time = object2 != null && object2.getBody() != null && object2.getBody().unwrap() != null
                        ? object2.getBody().unwrap()
                        : null;

                timeoutPair = Pair.of(time, System.currentTimeMillis());
                if (projectMap.isEmpty() || lastProjectSync < System.currentTimeMillis() - (30 * 60 * 10000)) {
                    projectMap.clear();
                    Response<TogglProject[]> object = new SimpleIOTemplate(TOGGL_ENDPOINT + "/workspaces/" + workspaceId + "/projects")
                            .addAuth(apiToken, "api_token").send(TogglProject[].class);
                    if (object != null && object.getCode() == 200 && object.getBody() != null) {
                        for (TogglProject project : object.getBody()) {
                            projectMap.put(project.getName(), project.getId());
                        }
                    }
                }
            }
        }
    }

    public synchronized TogglProject createProject(String projectName) {
        if(connected) {
            TogglProject project = new TogglProject((long) workspaceId, projectName);
            Response<TogglProjectDTO> projectDTOResponse = new SimpleIOTemplate(TOGGL_ENDPOINT + "/projects", SimpleIOTemplate.RequestMethod.POST)
                    .addAuth(apiToken, "api_token").send(new TogglProjectDTO(project), TogglProjectDTO.class);
            projectMap.put(projectName, projectDTOResponse.getBody().unwrap().getId());
            return projectDTOResponse.getBody().unwrap();
        }
        return null;
    }

    private Toggl4J(String apiToken, int workspaceId) {
        this.apiToken = apiToken;
        this.workspaceId = workspaceId;
    }

}
