package org.example.quiz.service;

import org.example.quiz.domain.entity.Option;
import org.example.quiz.domain.entity.Question;
import org.example.quiz.domain.repository.OptionRepository;
import org.example.quiz.domain.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOption_whenIdIsValid() {
        // given
        var id = 1L;
        var question = new Question();
        when(questionRepository.getReferenceById(id)).thenReturn(question);

        // when
        var result = questionService.getReferenceById(id);

        // then
        assertThat(result).isNotNull().isEqualTo(question);
        verify(questionRepository, times(1)).getReferenceById(id);
    }

    @Test
    void shouldThrowException_whenIdIsInvalid() {
        // given
        var id = 99L;
        when(questionRepository.getReferenceById(id)).thenThrow(new IllegalArgumentException("Invalid ID"));

        // when / then
        var exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionService.getReferenceById(id);
        });

        assertThat(exception.getMessage()).isEqualTo("Invalid ID");
        verify(questionRepository, times(1)).getReferenceById(id);
    }
}