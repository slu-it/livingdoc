package org.livingdoc.fixture.api.converter.predicate;

import org.livingdoc.fixture.api.converter.ConversionException;
import org.livingdoc.fixture.api.converter.common.FloatConverter;


public class FloatPredicateConverter extends AbstractNumberPredicateConverter<Float> {

    private FloatConverter converter = new FloatConverter();

    @Override
    protected boolean isNumberOfAppropriateType(String value) {
        try {
            convertToNumber(value);
            return true;
        } catch (ConversionException e) {
            return false;
        }
    }

    @Override
    protected Float convertToNumber(String value) {
        return converter.convert(value);
    }

}
