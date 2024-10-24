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
     * Default data model conversion
     *
     *  Only basic types will be used : String, Number, Boolean, Array, Object as Map.
     *
     *  For instance the object :
     *
     *  <pre>
     * {@code
          class Vehicle {
             private String plate;
             private int age;
             public Vehicle(int age, String plate) {
                 this.age = age;
                 this.plate = plate;
            }
             public String getPlate() { return plate; }
             public int getAge() { return age; }
             @Override
             public String toString() {
                return "Vehicle{age="+age+", plate='"+plate+"'}";
             }
         }
         Map<String, Object> dataModel = new HashMap<>();
         dataModel.put( "vehicle", new Vehicle( 10, "AA780BB" ) );
         Map<String, Object> jsonStyleDataModel = EvalScriptWithJsonDataModel.defaultDataModelConversion( dataModel );
         log.info( "originalDataModel : {}", dataModel );
         log.info( "jsonStyleDataModel : {}", jsonStyleDataModel );
       }
     * </pre>
     *
     * will result in :
     * originalDataModel : {vehicle=Vehicle{age=10, plate='AA780BB'}}
     * jsonStyleDataModel : {vehicle={plate=AA780BB, age=10}}
     *
     * @param dataModel     the data model to convert
     * @return              the data model converted to map of simple types
     */
    public static Map<String, Object> defaultDataModelConversion( Map<String, Object> dataModel ) {
        return MAPPER.convertValue( dataModel, LinkedHashMap.class );
    }

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
            return evalScript.handleEx( reader, defaultDataModelConversion( dataModel ) );
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
