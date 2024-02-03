package com.prograngers.backend.entity.solution;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prograngers.backend.exception.EnumTypeException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class LanguageConstantTest {
    @ParameterizedTest
    @CsvSource(value = {"Python,PYTHON", "Java,JAVA", "C++,CPP", "C,C"})
    void View에_알맞은_LanguageConstant를_얻을_수_있다(String view, LanguageConstant languageConstant) {
        assertThat(LanguageConstant.from(view)).isEqualTo(languageConstant);
    }

    @ParameterizedTest
    @ValueSource(strings = {"pytorch", "", "  "})
    void 잘못된_view에_대해_예외가_발생한다(String wrong) {
        assertThatThrownBy(() -> LanguageConstant.from(wrong)).isInstanceOf(EnumTypeException.class);
    }
}