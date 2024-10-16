package org.example.quiz.service;

import org.example.quiz.domain.entity.Participation;
import org.example.quiz.domain.repository.ParticipationRepository;
import org.example.quiz.exception.business.ParticipationNotFoundException;
import org.example.quiz.infrastructure.config.MessageSourceConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;

    @InjectMocks
    private ParticipationService participationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveAllParticipation_whenListIsValid() {
        // given
        var participation = List.of(new Participation(), new Participation());

        // when
        participationService.saveAll(participation);

        // then
        verify(participationRepository, times(1)).saveAll(participation);
    }

    @Test
    void shouldReturnTestResult_whenParticipationExist() {
        // given
        var testId = 1L;
        var studentId = 2L;
        var participationList = List.of(new Participation(), new Participation());
        when(participationRepository.findTestResultByTestIdAndStudentId(testId, studentId)).thenReturn(participationList);

        // when
        var result = participationService.getTestResultByTestIdAndStudentId(testId, studentId);

        // then
        assertThat(result).isNotEmpty().hasSize(2).isEqualTo(participationList);
        verify(participationRepository, times(1)).findTestResultByTestIdAndStudentId(testId, studentId);
    }

    @Test
    void shouldThrowException_whenNoParticipationFound() {
        // given
        var testId = 1L;
        var studentId = 2L;
        var exceptionMessage = "Participation not found";
        when(participationRepository.findTestResultByTestIdAndStudentId(testId, studentId)).thenReturn(List.of());
        mockStatic(MessageSourceConfiguration.class);
        when(MessageSourceConfiguration.getMessage("participation.not.found")).thenReturn(exceptionMessage);

        // when / then
        assertThatThrownBy(() -> participationService.getTestResultByTestIdAndStudentId(testId, studentId))
                .isInstanceOf(ParticipationNotFoundException.class)
                .hasMessage(exceptionMessage);

        verify(participationRepository, times(1)).findTestResultByTestIdAndStudentId(testId, studentId);
    }

    @Test
    void shouldReturnParticipationList_whenInvokingGetByTestIdAndStudentId() {
        // given
        var testId = 1L;
        var studentId = 2L;
        var participationList = List.of(new Participation(), new Participation());
        when(participationRepository.findTestResultByTestIdAndStudentId(testId, studentId)).thenReturn(participationList);

        // when
        var result = participationService.getByTestIdAndStudentId(testId, studentId);

        // then
        assertThat(result).isNotEmpty().hasSize(2).isEqualTo(participationList);
        verify(participationRepository, times(1)).findTestResultByTestIdAndStudentId(testId, studentId);
    }
}
