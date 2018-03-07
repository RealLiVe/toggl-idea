package rip.faith_in_humanity.time.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import rip.faith_in_humanity.time.TimeRegistration;

public class TimeMenuHandler extends AnAction implements DumbAware {

    public TimeMenuHandler() {
        super("MyMenu");
    }

    @Override
    public void update(AnActionEvent e) {
        TimeRegistration.INSTANCE.registerIcon(e.getPresentation());
        super.update(e);
    }


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        anActionEvent.getPresentation().setIcon(IconLoader.getIcon("/icons/iconTrackerGreen2.png"));
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        String txt = Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon());
        Messages.showMessageDialog(project, "Hello, " + txt + "!\n I am glad to see you.", "Information", Messages.getInformationIcon());
    }
}
