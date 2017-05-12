package org.livingdoc.fixture.api.converter.predicate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class DoublePredicateConverterTest {

    DoublePredicateConverter cut = new DoublePredicateConverter();

    @ParameterizedTest(name = "expression \"{0}\"  with x = {1} evaluates to \"{2}\"")
    @CsvSource(value = {//
        "5.0, 4.999, false",//
        "5.0, 5.0, true",//
        "5.0, 5.001, false",//

        "x < 5.0, 4.999, true",//
        "x < 5.0, 5.0, false",//
        "x < 5.0, 5.001, false",//

        "x <= 5.0, 4.999, true",//
        "x <= 5.0, 5.0, true",//
        "x <= 5.0, 5.001, false",//

        "x != 5.0, 4.999, true",//
        "x != 5.0, 5.0, false",//
        "x != 5.0, 5.001, true",//

        "x > 5.0, 4.999, false",//
        "x > 5.0, 5.0, false",//
        "x > 5.0, 5.001, true",//

        "x >= 5.0, 4.999, false",//
        "x >= 5.0, 5.0, true",//
        "x >= 5.0, 5.001, true",//

        "x > 4.999 & x < 5.001, 4.999, false",//
        "x > 4.999 & x < 5.001, 5.0, true",//
        "x > 4.999 & x < 5.001, 5.001, false",//

        "x > 4.999 && x < 5.001, 4.999, false",//
        "x > 4.999 && x < 5.001, 5.0, true",//
        "x > 4.999 && x < 5.001, 5.001, false",//

        "x > 5.0 | x < 5.0, 4.999, true",//
        "x > 5.0 | x < 5.0, 5.0, false",//
        "x > 5.0 | x < 5.0, 5.001, true",//

        "x > 5.0 || x < 5.0, 4.999, true",//
        "x > 5.0 || x < 5.0, 5.0, false",//
        "x > 5.0 || x < 5.0, 5.001, true",//

        "x > 1.0 & (x <= 3.0 | x > 100.0), 1.0, false",//
        "x > 1.0 & (x <= 3.0 | x > 100.0), 2.0, true",//
        "x > 1.0 & (x <= 3.0 | x > 100.0), 3.0, true",//
        "x > 1.0 & (x <= 3.0 | x > 100.0), 4.0, false",//
        "x > 1.0 & (x <= 3.0 | x > 100.0), 100.0, false",//
        "x > 1.0 & (x <= 3.0 | x > 100.0), 101.0, true",//
    })
    void allTests(String expression, Double testValue, boolean expectedResult) {
        Predicate<Double> predicate = cut.convert(expression);
        assertThat(predicate.test(testValue)).isEqualTo(expectedResult);
    }

    @Test
    void spacesAreNotNeeded() {
        Predicate<Double> predicate = cut.convert("x>1&(x<=3|x>100)");
        assertThat(predicate.test(1.0d)).isFalse();
        assertThat(predicate.test(2.0d)).isTrue();
        assertThat(predicate.test(3.0d)).isTrue();
        assertThat(predicate.test(4.0d)).isFalse();
        assertThat(predicate.test(100.0d)).isFalse();
        assertThat(predicate.test(101.0d)).isTrue();
    }

}
