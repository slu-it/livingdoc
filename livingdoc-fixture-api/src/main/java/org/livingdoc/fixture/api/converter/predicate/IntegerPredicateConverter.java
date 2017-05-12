package org.livingdoc.fixture.api.converter.predicate;

import org.livingdoc.fixture.api.converter.ConversionException;
import org.livingdoc.fixture.api.converter.common.IntegerConverter;


public class IntegerPredicateConverter extends AbstractNumberPredicateConverter<Integer> {

    private IntegerConverter converter = new IntegerConverter();

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
    protected Integer convertToNumber(String value) {
        return converter.convert(value);
    }

}
