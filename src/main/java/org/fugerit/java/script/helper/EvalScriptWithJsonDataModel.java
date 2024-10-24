package org.fugerit.java.script.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * EvalScript decorator.
 *
 * The Map dataModel will be transformed into a json styled LinkedHashMap.
 */
@Slf4j
public class EvalScriptWithJsonDataModel implements EvalScript {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private EvalScript evalScript;

    /**
     * Add decoration to a give EvalScript
     *
     * @param evalScript    the EvalScript instance to decorate
     */
    public EvalScriptWithJsonDataModel(EvalScript evalScript) {
        this.evalScript = evalScript;
        log.debug( "decorator for {}", evalScript );
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

    /**
     * It will decorate a EvalScriptWithDataModel instance.
     *
     * @param scriptExtension           the extension to be used for the ScriptEngine
     * @param dataModelBindingName      the data model binding name
     * @return  the new EvalScript
     */
    public static EvalScript newEvalScriptWithJsonDataModel( String scriptExtension, String dataModelBindingName ) {
        return new EvalScriptWithJsonDataModel( new EvalScriptWithDataModel( scriptExtension, dataModelBindingName ) );
    }

    /**
     * It will decorate a EvalScriptWithDataModel instance with default data model binding name.
     *
     * @param scriptExtension           the extension to be used for the ScriptEngine
     * @return  the new EvalScript
     */
    public static EvalScript newEvalScriptWithJsonDataModel( String scriptExtension ) {
        return new EvalScriptWithJsonDataModel( new EvalScriptWithDataModel( scriptExtension ) );
    }

}
