package com.github.reallive.idea.timetrack.toggl.exception;

public class TogglException extends Exception {
    public TogglException() {
    }

    public TogglException(String message) {
        super(message);
    }

    public TogglException(String message, Throwable cause) {
        super(message, cause);
    }
}
