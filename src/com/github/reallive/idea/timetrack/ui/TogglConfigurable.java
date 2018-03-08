package com.github.reallive.idea.timetrack.ui;

import com.github.reallive.idea.timetrack.TogglPlugIn;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TogglConfigurable implements SearchableConfigurable {

    private TogglConfGUI togglConfGUI;

    @NotNull
    @Override
    public String getId() {
        return "preference.TogglConfigurable";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Toggl Time Tracker";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        togglConfGUI = new TogglConfGUI(TogglConfigState.getInstance()).init();
        return togglConfGUI.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return togglConfGUI.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        TogglPlugIn.INSTANCE.update(togglConfGUI.apply());
    }

    @Override
    public void disposeUIResources() {
        togglConfGUI = null;
    }
}
