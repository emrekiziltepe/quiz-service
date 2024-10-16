package org.example.quiz.service;

import org.example.quiz.domain.entity.Option;
import org.example.quiz.domain.repository.OptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OptionServiceTest {

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private OptionService optionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOption_whenIdIsValid() {
        // given
        var id = 1L;
        var option = new Option();
        when(optionRepository.getReferenceById(id)).thenReturn(option);

        // when
        var result = optionService.getReferenceById(id);

        // then
        assertThat(result).isNotNull().isEqualTo(option);
        verify(optionRepository, times(1)).getReferenceById(id);
    }

    @Test
    void shouldThrowException_whenIdIsInvalid() {
        // given
        var id = 99L;
        when(optionRepository.getReferenceById(id)).thenThrow(new IllegalArgumentException("Invalid ID"));

        // when / then
        var exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            optionService.getReferenceById(id);
        });

        assertThat(exception.getMessage()).isEqualTo("Invalid ID");
        verify(optionRepository, times(1)).getReferenceById(id);
    }
}