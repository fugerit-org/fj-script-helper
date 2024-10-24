package org.fugerit.java.script.helper;

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

/**
 * Simple implementation of EvalScript.
 *
 * It relies on extension based ScriptEngine (ScriptEngineManager.getEngineByExtension( this.scriptExtension ))
 *
 * Data model binding name can be customized, default is 'data'.
 *
 */
@Slf4j
public class EvalScriptWithDataModel implements EvalScript {

    public static final String DEFAULT_DATA_MODEL_BINDING_NAME = "data";

    private String scriptExtension;

    private String dataModelBindingName;

    /**
     * It will create a EvalScriptWithDataModel
     *
     * @param scriptExtension           the extension to be used for the ScriptEngine
     * @param dataModelBindingName      the data model binding name
     */
    public EvalScriptWithDataModel(String scriptExtension, String dataModelBindingName) {
        this.scriptExtension = scriptExtension;
        this.dataModelBindingName = dataModelBindingName;
        log.debug( "use scriptExtension : [{}], use dataModelBindingName : [{}]s", scriptExtension, dataModelBindingName );
    }

    /**
     * It will create a EvalScriptWithDataModel with the default data model binding name.
     *
     * @param scriptExtension           the extension to be used for the ScriptEngine
     */
    public EvalScriptWithDataModel(String scriptExtension) {
        this( scriptExtension, DEFAULT_DATA_MODEL_BINDING_NAME );
    }

    @Override
    public Object handleEx(Reader reader, Map<String, Object> dataModel) throws ScriptException {
        try {
            SimpleCheckpoint checkpoint = new SimpleCheckpoint();
            ScriptEngineManager manager = new ScriptEngineManager();
            log.debug( "{} create script manager : {}", this.scriptExtension, checkpoint.getFormatTimeDiffMillis() );
            ScriptEngine engine =  manager.getEngineByExtension( this.scriptExtension );
            log.debug( "{} create script engine : {}", this.scriptExtension, checkpoint.getFormatTimeDiffMillis() );
            if ( dataModel != null ) {
                Bindings bindings = engine.createBindings();
                log.debug( "{} create script bindings : {}", this.scriptExtension, checkpoint.getFormatTimeDiffMillis() );
                bindings.put( this.dataModelBindingName, dataModel );
                engine.setBindings( bindings, ScriptContext.ENGINE_SCOPE );
                log.debug( "{} set binding with name {}, time: {}", this.scriptExtension, this.dataModelBindingName, checkpoint.getFormatTimeDiffMillis() );
            }
            Object obj = engine.eval( StreamIO.readString( reader ) );
            log.debug( "{} eval script : {}", this.scriptExtension, checkpoint.getFormatTimeDiffMillis() );
            return obj;
        } catch (Exception e) {
            throw new ScriptException( String.format( "Exception running handleEx %s", e ) ,e);
        }
    }

}
