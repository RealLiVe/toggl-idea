package com.github.reallive.idea.timetrack.ui;

import com.intellij.openapi.util.Comparing;

import javax.swing.*;

public class TogglConfGUI {
    private JPanel rootPanel;
    private JTextField apiKey;
    private JLabel apiKeyLabel;
    private TogglConfigState configState;

    public TogglConfGUI(TogglConfigState state) {
        this.configState = state;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public boolean isModified() {
        return !Comparing.equal(apiKey.getText(), configState.apiKey);
    }

    public String apply() {
        configState.apiKey = apiKey.getText();
        return configState.apiKey;
    }

    public TogglConfGUI init() {
        apiKey.setText(configState.apiKey);
        return this;
    }
}
