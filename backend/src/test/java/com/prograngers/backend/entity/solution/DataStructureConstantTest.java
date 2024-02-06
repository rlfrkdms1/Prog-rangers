package com.prograngers.backend.entity.solution;

import static com.prograngers.backend.entity.solution.DataStructureConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prograngers.backend.exception.enumtype.DataStructureNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DataStructureConstantTest {
    @ParameterizedTest
    @CsvSource({"리스트,LIST", "배열,ARRAY", "스택,STACK"})
    void 주어진_뷰에_알맞은_상수를_얻을_수_있다(String view, DataStructureConstant constant) {
        assertThat(from(view)).isEqualTo(constant);
    }

    @ParameterizedTest
    @ValueSource(strings = {"바 보", "", "    "})
    void 잘못된_뷰에_대해_예외가_발생한다(String view) {
        assertThatThrownBy(() -> from(view)).isInstanceOf(DataStructureNotFoundException.class);
    }
}