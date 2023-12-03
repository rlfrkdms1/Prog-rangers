package com.prograngers.backend.entity.solution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LanguageConstantTest {
    @ParameterizedTest
    @CsvSource(value = {"Python,PYTHON", "Java,JAVA", "C++,CPP", "C,C"})
    void View에_알맞은_LanguageConstant를_얻을_수_있다(String view, LanguageConstant languageConstant) {
        assertThat(LanguageConstant.from(view)).isEqualTo(languageConstant);
    }

}