package org.fugerit.java.script.helper;

public class ScriptException extends Exception {

    public ScriptException() {
    }

    public ScriptException(Throwable cause) {
        super(cause);
    }

    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }
}
