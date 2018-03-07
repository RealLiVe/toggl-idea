package rip.faith_in_humanity.time.toggl.exception;

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
