package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface TimePluginIcons {

    String PATH_IDLE_ICON = "/icons/iconTracker.png";
    String PATH_OKAY_ICON = "/icons/iconTrackerGreen2.png";
    String PATH_WARN_ICON = "/icons/iconTrackerYellow.png";

    Icon TRACK_BUTTON_IDLE = IconLoader.getIcon(PATH_IDLE_ICON);
    Icon TRACK_BUTTON_ACTIVE = IconLoader.getIcon(PATH_OKAY_ICON);
    Icon TRACK_BUTTON_WARN = IconLoader.getIcon(PATH_WARN_ICON);
}
