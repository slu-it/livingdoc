package org.livingdoc.fixture.api.converter.predicate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class LongPredicateConverterTest {

    LongPredicateConverter cut = new LongPredicateConverter();

    @ParameterizedTest(name = "expression \"{0}\"  with x = {1} evaluates to \"{2}\"")
    @CsvSource(value = {//
        "5, 4, false",//
        "5, 5, true",//
        "5, 6, false",//

        "x < 5, 4, true",//
        "x < 5, 5, false",//
        "x < 5, 6, false",//

        "x <= 5, 4, true",//
        "x <= 5, 5, true",//
        "x <= 5, 6, false",//

        "x != 5, 4, true",//
        "x != 5, 5, false",//
        "x != 5, 6, true",//

        "x > 5, 4, false",//
        "x > 5, 5, false",//
        "x > 5, 6, true",//

        "x >= 5, 4, false",//
        "x >= 5, 5, true",//
        "x >= 5, 6, true",//

        "x > 4 & x < 6, 4, false",//
        "x > 4 & x < 6, 5, true",//
        "x > 4 & x < 6, 6, false",//

        "x > 4 && x < 6, 4, false",//
        "x > 4 && x < 6, 5, true",//
        "x > 4 && x < 6, 6, false",//

        "x > 5 | x < 5, 4, true",//
        "x > 5 | x < 5, 5, false",//
        "x > 5 | x < 5, 6, true",//

        "x > 5 || x < 5, 4, true",//
        "x > 5 || x < 5, 5, false",//
        "x > 5 || x < 5, 6, true",//

        "x > 1 & (x <= 3 | x > 100), 1, false",//
        "x > 1 & (x <= 3 | x > 100), 2, true",//
        "x > 1 & (x <= 3 | x > 100), 3, true",//
        "x > 1 & (x <= 3 | x > 100), 4, false",//
        "x > 1 & (x <= 3 | x > 100), 100, false",//
        "x > 1 & (x <= 3 | x > 100), 101, true",//
    })
    void allTests(String expression, Long testValue, boolean expectedResult) {
        Predicate<Long> predicate = cut.convert(expression);
        assertThat(predicate.test(testValue)).isEqualTo(expectedResult);
    }

    @Test
    void spacesAreNotNeeded() {
        Predicate<Long> predicate = cut.convert("x>1&(x<=3|x>100)");
        assertThat(predicate.test(1L)).isFalse();
        assertThat(predicate.test(2L)).isTrue();
        assertThat(predicate.test(3L)).isTrue();
        assertThat(predicate.test(4L)).isFalse();
        assertThat(predicate.test(100L)).isFalse();
        assertThat(predicate.test(101L)).isTrue();
    }

}
