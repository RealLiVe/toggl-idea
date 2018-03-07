package rip.faith_in_humanity.time;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.IconLoader;
import com.intellij.tasks.LocalTask;
import com.intellij.tasks.TaskManager;
import com.intellij.tasks.timeTracking.TimeTrackingManager;
import icons.TimePluginIcons;
import org.jetbrains.annotations.NotNull;
import rip.faith_in_humanity.time.ui.TimeMenuHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.sleep;

public class TimeRegistration implements ApplicationComponent {

    public static TimeRegistration INSTANCE = null;

    public static final String COMPONENT_NAME = "FaithInHumanity-Time";

    public static final String ACTION_NAME = "FaithInHumanity-Menu";

    public List<Presentation> presentations = Collections.synchronizedList(new LinkedList<Presentation>());

    public void registerIcon(Presentation presentation) {
        if (!presentations.stream().anyMatch(x -> x == presentation)) {
            presentations.add(presentation);
        }
    }

    public Icon changeItem() {
        return IconLoader.getIcon(icons[atomicInteger.getAndIncrement() % icons.length]);
    }

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    private static String[] icons = {TimePluginIcons.PATH_IDLE_ICON, TimePluginIcons.PATH_OKAY_ICON, TimePluginIcons.PATH_WARN_ICON};
    private final AtomicReference<TimeEntity> activeTimeEntity = new AtomicReference<>();

    static class TimeEntity {
        private String projectName;

        private String taskName;

        private long startTime;

        public TimeEntity(String projectName, String taskName) {
            this.projectName = projectName;
            this.taskName = taskName;
            this.startTime = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return "TimeEntity{" +
                    "" + (projectName == null ? "null " : projectName)
                    + " (" + (taskName == null ? "null" : taskName) + ")}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TimeEntity that = (TimeEntity) o;

            if (projectName != null ? !projectName.equals(that.projectName) : that.projectName != null) return false;
            return taskName != null ? taskName.equals(that.taskName) : that.taskName == null;
        }

        @Override
        public int hashCode() {
            int result = projectName != null ? projectName.hashCode() : 0;
            result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
            return result;
        }

        public static TimeEntity of(Project project) {
            if (project != null && !project.isDisposed()) {
                LocalTask task = TaskManager.getManager(project).getActiveTask();
                return new TimeEntity(project.getName(), task.getSummary());
            }
            return null;
        }

        public long runtime() {
            return System.currentTimeMillis() - startTime;
        }
    }

    @Override
    public void initComponent() {
        TimeRegistration.INSTANCE = this;
        ActionManager am = ActionManager.getInstance();
        TimeMenuHandler handler = new TimeMenuHandler();
        am.registerAction(ACTION_NAME, handler);

//        // Gets an instance of the WindowMenu action group.
//        DefaultActionGroup windowM = (DefaultActionGroup) am.getAction("Test");
//
//        // Adds a separator and a new menu command to the WindowMenu group on the main menu.
//        windowM.addSeparator();
//        windowM.add(handler);

        ApplicationManager.getApplication().executeOnPooledThread(() -> {
                    while (true) {
                        RunnableFuture<Project> affectedProject = new FutureTask<>(() -> {
                            Icon a = TimeRegistration.INSTANCE.changeItem();
                            presentations.forEach(x -> x.setIcon(a));
                            DataContext dataContext = DataManager.getInstance().getDataContext();
                            return (Project) dataContext.getData(DataConstants.PROJECT);
                        });
                        ApplicationManager.getApplication().invokeLater(affectedProject);
                        try {
                            Project project = affectedProject.get();
                            TimeEntity timeEntity = TimeEntity.of(project);
                            if (timeEntity != null && !timeEntity.equals(activeTimeEntity.get())) {
                                TimeEntity oldEntity = activeTimeEntity.get();
                                if (oldEntity != null) {
                                    System.out.println("Switched from " + oldEntity.toString() + " to "
                                            + timeEntity.toString() + " after " + (oldEntity.runtime() / 1000.0) + " s");
                                } else {
                                    System.out.println("Start new Session : " + timeEntity);
                                }
                                activeTimeEntity.set(timeEntity);
                            }
                        } catch (Exception e) {
                            System.err.println("There is an error:" + e.getMessage());
                        }
                        sleep(1000);
                    }
                }
        );
    }

    @NotNull
    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }
}
