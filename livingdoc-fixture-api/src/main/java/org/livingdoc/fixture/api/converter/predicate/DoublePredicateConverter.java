package org.livingdoc.fixture.api.converter.predicate;

import org.livingdoc.fixture.api.converter.ConversionException;
import org.livingdoc.fixture.api.converter.common.DoubleConverter;


public class DoublePredicateConverter extends AbstractNumberPredicateConverter<Double> {

    private DoubleConverter converter = new DoubleConverter();

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
    protected Double convertToNumber(String value) {
        return converter.convert(value);
    }

}
