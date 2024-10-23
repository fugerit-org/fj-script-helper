package org.fugerit.java.script.helper;

import org.fugerit.java.core.function.SafeFunction;

import java.io.Reader;
import java.util.Map;

public interface EvalScript {

    default Object handle(Reader reader, Map<String, Object> dataModel) {
        return SafeFunction.get( () -> handleEx(reader, dataModel) );
    }

    default Object handle(Reader reader) {
        return handle(reader, null);
    }

    Object handleEx(Reader reader, Map<String, Object> dataModel) throws ScriptException;

    default Object handleEx(Reader reader) throws ScriptException {
        return handleEx(reader, null);
    }

}
