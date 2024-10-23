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

    private String scriptExtension;

    public EvalScriptWithJsonDataModel(String scriptExtension) {
        this.scriptExtension = scriptExtension;
        log.debug( "use script extension [{}]", scriptExtension );
    }

    @Override
    public Object evalKtsEx(Reader reader, Map<String, Object> dataModel) throws ScriptException {
        try {
            SimpleCheckpoint checkpoint = new SimpleCheckpoint();
            ScriptEngineManager manager = new ScriptEngineManager();
            log.debug( "kts create script manager : {}", checkpoint.getFormatTimeDiffMillis() );
            ScriptEngine engine =  manager.getEngineByExtension( this.scriptExtension );
            log.debug( "kts create script engine : {}", checkpoint.getFormatTimeDiffMillis() );
            if ( dataModel != null ) {
                Bindings bindings = engine.createBindings();
                log.debug( "kts create script bindings : {}", checkpoint.getFormatTimeDiffMillis() );
                LinkedHashMap<String, Object> data = MAPPER.convertValue( dataModel, LinkedHashMap.class );
                log.debug( "kts read json data : {}", checkpoint.getFormatTimeDiffMillis() );
                bindings.put( "data", data );
                engine.setBindings( bindings, ScriptContext.ENGINE_SCOPE );
            }
            log.debug( "kts set bindings : {}", checkpoint.getFormatTimeDiffMillis() );
            Object obj = engine.eval( StreamIO.readString( reader ) );
            log.debug( "kts eval script : {}", checkpoint.getFormatTimeDiffMillis() );
            return obj;
        } catch (Exception e) {
            throw new ScriptException( String.format( "Exception running evalKts %s", e ) ,e);
        }
    }

}
