package org.fugerit.java.script.helper;

import org.fugerit.java.core.function.SafeFunction;

import java.io.Reader;
import java.util.Map;

/**
 * Simple interface for script handling.
 */
public interface EvalScript {

    /**
     * It will handle a script and bind a data model.
     *
     * Any exception will be converted to {@link org.fugerit.java.core.cfg.ConfigRuntimeException}
     *
     * @param reader        the reader over script
     * @param dataModel     the data model to bind
     * @return              the result of script processing
     */
    default Object handle(Reader reader, Map<String, Object> dataModel) {
        return SafeFunction.get( () -> handleEx(reader, dataModel) );
    }

    /**
     * It will handle a script with no data model
     *
     * Any exception will be converted to {@link org.fugerit.java.core.cfg.ConfigRuntimeException}
     *
     * @param reader        the reader over script
     * @return              the result of script processing
     */
    default Object handle(Reader reader) {
        return handle(reader, null);
    }

    /**
     * It will handle a script and bind a data model.
     *
     * Any class implementing EvalScript will need to provide at least this method.
     *
     * @param reader        the reader over script
     * @param dataModel     the data model to bind
     * @return              the result of script processing
     * @throws ScriptException  in case of script handling issues
     */
    Object handleEx(Reader reader, Map<String, Object> dataModel) throws ScriptException;

    /**
     * It will handle a script with no data model.
     *
     * @param reader        the reader over script
     * @return              the result of script processing
     * @throws ScriptException  in case of script handling issues
     */
    default Object handleEx(Reader reader) throws ScriptException {
        return handleEx(reader, null);
    }

}
