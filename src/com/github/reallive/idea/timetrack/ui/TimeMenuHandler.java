package com.github.reallive.idea.timetrack.ui;

import com.github.reallive.idea.timetrack.TogglPlugIn;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;

public class TimeMenuHandler extends AnAction implements DumbAware {

    public TimeMenuHandler() {
        super("MyMenu");
    }

    @Override
    public void update(AnActionEvent e) {
        TogglPlugIn.INSTANCE.registerIcon(e.getPresentation());
        super.update(e);
    }


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        TogglPlugIn.INSTANCE.clickTogglButton();
//        anActionEvent.getPresentation().setIcon(IconLoader.getIcon("/icons/iconTrackerGreen2.png"));
//        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
//        String txt = Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon());
//        Messages.showMessageDialog(project, "Hello, " + txt + "!\n I am glad to see you.", "Information", Messages.getInformationIcon());
    }
}
