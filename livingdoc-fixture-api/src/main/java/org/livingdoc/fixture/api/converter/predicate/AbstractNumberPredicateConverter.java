package org.livingdoc.fixture.api.converter.predicate;

import java.lang.reflect.AnnotatedElement;
import java.util.function.Predicate;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.livingdoc.fixture.api.converter.ConversionException;
import org.livingdoc.fixture.api.converter.TypeConverter;


public abstract class AbstractNumberPredicateConverter<T extends Number> implements TypeConverter<Predicate<T>> {

    private static final ScriptEngine JAVA_SCRIP_ENGINE = new ScriptEngineManager().getEngineByName("nashorn");

    @Override
    public Predicate<T> convert(String value, AnnotatedElement element) throws ConversionException {
        return number -> {
            String trimmedValue = value.trim();
            if (isNumberOfAppropriateType(trimmedValue)) {
                return evaluateAsEquals(number, trimmedValue);
            }
            return evaluateExpression(number, trimmedValue);
        };
    }

    protected abstract boolean isNumberOfAppropriateType(String value);

    private boolean evaluateAsEquals(T number, String value) {
        T convertedValue = convertToNumber(value);
        return number.equals(convertedValue);
    }

    protected abstract T convertToNumber(String value);

    private Boolean evaluateExpression(T number, String expression) {
        String script = toScript(number, expression);
        try {
            return ( boolean ) JAVA_SCRIP_ENGINE.eval(script);
        } catch (ScriptException | ClassCastException e) {
            throw new ConversionException(e);
        }
    }

    private String toScript(T number, String expression) {
        String script = expression;
        script = script.replaceAll("([^\\|])\\|([^\\|])", "$1||$2");
        script = script.replaceAll("([^\\&])\\&([^\\&])", "$1&&$2");
        script = script.replaceAll("x", String.valueOf(number));
        script += ';';
        return script;
    }

}
