package com.github.reallive.idea.timetrack;

import com.github.reallive.idea.timetrack.toggl.Toggl4J;
import com.github.reallive.idea.timetrack.toggl.model.TogglTime;
import com.github.reallive.idea.timetrack.ui.TogglConfigState;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.LoggerRt;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.tasks.LocalTask;
import com.intellij.tasks.TaskManager;
import com.intellij.util.containers.WeakList;
import icons.TimePluginIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class TogglPlugIn implements ApplicationComponent {

    private static final LoggerRt logger = LoggerRt.getInstance(TogglPlugIn.class);

    public void update(String apiKey) {
        toggl4J.connect(apiKey);
    }

    public enum State {
        IDLE, RUN, WARN
    }

    public static TogglPlugIn INSTANCE = null;

    private Toggl4J toggl4J = new Toggl4J();

    private WeakList<Presentation> presentations = new WeakList<>();

    private final Timer timer = new Timer();

    public void registerIcon(Presentation presentation) {
        for (Presentation p : presentations) {
            if (presentation == p) {
                return;
            }
        }
        logger.info("new icon registered in plug-in.");
        presentations.add(presentation);
    }

    private static Icon loadIcon(State state) {
        switch (state) {
            case RUN:
                return IconLoader.getIcon(TimePluginIcons.PATH_OKAY_ICON);
            case WARN:
                return IconLoader.getIcon(TimePluginIcons.PATH_WARN_ICON);
            default:
                return IconLoader.getIcon(TimePluginIcons.PATH_IDLE_ICON);
        }
    }

    public synchronized void clickTogglButton() {
        logger.info("toggl button was clicked.");
        TogglConfigState togglConfiguration = TogglConfigState.getInstance().getState();
        toggl4J.connect(togglConfiguration == null ? null : togglConfiguration.apiKey);
        if (toggl4J.get() == null) {
            try {
                synchronize(true);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            toggl4J.stop();
            ApplicationManager.getApplication().invokeLater(() -> {
                if (toggl4J.get() == null) {
                    presentations.forEach(x -> x.setIcon(loadIcon(State.IDLE)));
                } else {
                    presentations.forEach(x -> x.setIcon(loadIcon(State.RUN)));
                }
            });
        }

    }

    private synchronized void synchronize(boolean forceStart) throws ExecutionException, InterruptedException {
        logger.info("toggl synchronization(force=" + forceStart + ") started");
        RunnableFuture<Project> affectedProject = new FutureTask<>(() -> {
            DataContext dataContext = DataManager.getInstance().getDataContext();
            return (Project) dataContext.getData(DataConstants.PROJECT);
        });
        ApplicationManager.getApplication().invokeAndWait(affectedProject);
        Project project = affectedProject.get();
        if (project != null) {
            LocalTask activeTask = TaskManager.getManager(project).getActiveTask();
            TogglTime togglTime = toggl4J.get();
            if (togglTime != null) {
                String summary = activeTask.getPresentableName();
                if ("".equalsIgnoreCase(summary) || summary.equalsIgnoreCase("Default")
                        || summary.equalsIgnoreCase("Default task")) {
                    summary = "No ticket (" + project.getName() + ")";
                }
                if (!summary.equalsIgnoreCase(togglTime.getDescription())) {
                    logger.info("switch to " + summary);
                    toggl4J.startEntry(project.getName(), summary);
                }
            } else if (forceStart) {
                String summary = activeTask.getPresentableId();
                if ("".equalsIgnoreCase(summary) || summary.equalsIgnoreCase("Default")
                        || summary.equalsIgnoreCase("Default task")) {
                    summary = "No ticket (" + project.getName() + ")";
                }
                toggl4J.startEntry(project.getName(), summary);
            }
        }
        ApplicationManager.getApplication().invokeLater(() -> {
            if (toggl4J.get() == null) {
                presentations.forEach(x -> x.setIcon(loadIcon(State.IDLE)));
            } else {
                presentations.forEach(x -> x.setIcon(loadIcon(State.RUN)));
            }
        });
        logger.info("toggl synchronization(force=" + forceStart + ") started");
    }

    static class TogglRefreshJob extends TimerTask {
        @Override
        public void run() {
            try {
                TogglPlugIn.INSTANCE.synchronize(false);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public void initComponent() {
        toggl4J = new Toggl4J();
        TogglPlugIn.INSTANCE = this;
        TogglConfigState togglConfiguration = TogglConfigState.getInstance().getState();
        if (togglConfiguration != null && togglConfiguration.apiKey != null
                && !"".equalsIgnoreCase(togglConfiguration.apiKey)) {
            toggl4J.connect(togglConfiguration.apiKey);
        }
        timer.scheduleAtFixedRate(new TogglRefreshJob(), 0, 5000);
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "Toggl Time Tracking Context";
    }

    @Override
    public void disposeComponent() {

    }
}
