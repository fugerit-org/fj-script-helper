package org.fugerit.java.script.helper;

import org.fugerit.java.core.function.SafeFunction;

import java.io.Reader;
import java.util.Map;

public interface EvalScript {

    default Object evalKts(Reader reader, Map<String, Object> dataModel) {
        return SafeFunction.get( () -> evalKtsEx(reader, dataModel) );
    }

    default Object evalKts(Reader reader) {
        return evalKts(reader, null);
    }

    Object evalKtsEx(Reader reader, Map<String, Object> dataModel) throws ScriptException;

    default Object evalKtsEx(Reader reader) throws ScriptException {
        return evalKtsEx(reader, null);
    }

}
