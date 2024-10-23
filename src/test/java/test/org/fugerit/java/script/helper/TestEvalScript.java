package test.org.fugerit.java.script.helper;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.script.helper.EvalScript;
import org.fugerit.java.script.helper.EvalScriptWithJsonDataModel;
import org.fugerit.java.script.helper.ScriptException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
class TestEvalScript {

    private EvalScript evalKts = new EvalScriptWithJsonDataModel( "kts" );

    private String testWorkerSimpleScript(Function<Reader, Object> evalFun) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(ClassHelper.loadFromDefaultClassLoader( "kts/sample-1.kts" ))) {
            Object result = evalFun.apply( reader );
            String xml = result.toString();
            log.info( "xml : \n{}", xml );
            return xml;
        }
    }

    @Test
    void testEvalkts() throws IOException {
        Assertions.assertNotNull( this.testWorkerSimpleScript( r -> this.evalKts.evalKts( r ) ) );
        Assertions.assertNotNull( this.testWorkerSimpleScript( r -> SafeFunction.get( () -> this.evalKts.evalKtsEx( r ) ) ) );
    }

    @Test
    void testEvalktsWithDataModel() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(ClassHelper.loadFromDefaultClassLoader( "kts/sample-1-with-data-model.kts" ))) {
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put( "docTitle", "My custom title" );
            Object result = evalKts.evalKts( reader, dataModel );
            String xml = result.toString();
            log.info( "xml from data model : \n{}", xml );
            Assertions.assertNotNull( xml );
        }
    }

    @Test
    void testEvalktsWithException() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(ClassHelper.loadFromDefaultClassLoader( "kts/sample-1-with-data-model.kts" ))) {
            Assertions.assertThrows( ScriptException.class, () -> evalKts.evalKtsEx( reader ) ) ;
        }
    }

    @Test
    void testScriptException() {
        Assertions.assertNotNull( new ScriptException() );
        Assertions.assertNotNull( new ScriptException( new IOException() ) );
        Assertions.assertNotNull( new ScriptException( "test 1" ) );
        Assertions.assertNotNull( new ScriptException( "test 2", new ConfigException()) );
    }

}
