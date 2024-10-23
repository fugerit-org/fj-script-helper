package org.fugerit.java.script.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.util.checkpoint.SimpleCheckpoint;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class EvalScriptWithJsonDataModel implements EvalScript {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private EvalScript evalScript;

    public EvalScriptWithJsonDataModel(EvalScript evalScript) {
        this.evalScript = evalScript;
    }

    @Override
    public Object handleEx(Reader reader, Map<String, Object> dataModel) throws ScriptException {
        if ( dataModel != null ) {
            LinkedHashMap<String, Object> data = MAPPER.convertValue( dataModel, LinkedHashMap.class );
            return evalScript.handleEx( reader, data );
        } else {
            return evalScript.handleEx( reader );
        }
    }

    public static EvalScript newEvalScriptWithJsonDataModel( String scriptExtension, String dataModelBindingName ) {
        return new EvalScriptWithJsonDataModel( new EvalScriptWithDataModel( scriptExtension, dataModelBindingName ) );
    }

    public static EvalScript newEvalScriptWithJsonDataModel( String scriptExtension ) {
        return new EvalScriptWithJsonDataModel( new EvalScriptWithDataModel( scriptExtension ) );
    }

}
