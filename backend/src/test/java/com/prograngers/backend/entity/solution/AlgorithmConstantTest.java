package com.prograngers.backend.entity.solution;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prograngers.backend.exception.EnumTypeException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class AlgorithmConstantTest {

    @ParameterizedTest
    @CsvSource({"버블 정렬,BUBBLE_SORT", "선택 정렬,SELECTION_SORT", "삽입 정렬,INSERTION_SORT"})
    void 주어진_뷰에_알맞은_상수를_얻을_수_있다(String view, AlgorithmConstant constant) {
        assertThat(from(view)).isEqualTo(constant);
    }

    @ParameterizedTest
    @ValueSource(strings = {"바보 정렬", "", "    "})
    void 잘못된_뷰에_대해_예외가_발생한다(String view) {
        assertThatThrownBy(() -> from(view)).isInstanceOf(EnumTypeException.class);
    }
}