package com.github.reallive.idea.timetrack.ui;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
        name = "TogglConfigProvider",
        storages = {
                @Storage("TogglConfig.xml")}
)
public class TogglConfigState implements PersistentStateComponent<TogglConfigState> {

    public String apiKey;

    @Nullable
    @Override
    public TogglConfigState getState() {
        return this;
    }

    @Override
    public void loadState(TogglConfigState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static TogglConfigState getInstance() {
        return ServiceManager.getService(TogglConfigState.class);
    }
}
