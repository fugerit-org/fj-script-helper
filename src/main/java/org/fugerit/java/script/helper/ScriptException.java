package org.fugerit.java.script.helper;

/**
 * Exception used by {@link EvalScript}
 */
public class ScriptException extends Exception {

    /**
     * Default constructor
     */
    public ScriptException() {
        super();
    }

    /**
     * Constructor using only the cause
     *
     * @param cause the cause of this exception
     */
    public ScriptException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor using only a message
     *
     * @param message the message of this exception
     */
    public ScriptException(String message) {
        super(message);
    }

    /**
     * Constructor using message and cause
     *
     * @param message   the message of this exception
     * @param cause     the cause of this exception
     */
    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }
}
