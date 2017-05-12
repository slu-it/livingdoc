package org.livingdoc.fixture.api.converter.predicate;

import org.livingdoc.fixture.api.converter.ConversionException;
import org.livingdoc.fixture.api.converter.common.LongConverter;


public class LongPredicateConverter extends AbstractNumberPredicateConverter<Long> {

    private LongConverter converter = new LongConverter();

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
    protected Long convertToNumber(String value) {
        return converter.convert(value);
    }

}
