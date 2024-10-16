package org.example.quiz.service;

import org.example.quiz.data.dto.QuestionAnswerDto;
import org.example.quiz.data.request.TestAnswerRequest;
import org.example.quiz.data.response.TestQuestionResponse;
import org.example.quiz.data.response.TestResponse;
import org.example.quiz.domain.entity.Option;
import org.example.quiz.domain.entity.Participation;
import org.example.quiz.domain.entity.Question;
import org.example.quiz.domain.entity.Student;
import org.example.quiz.domain.entity.Test;
import org.example.quiz.domain.repository.TestRepository;
import org.example.quiz.exception.business.TestAlreadySubmittedException;
import org.example.quiz.exception.business.TestNotFoundException;
import org.example.quiz.infrastructure.config.MessageSourceConfiguration;
import org.example.quiz.mapper.TestMapper;
import org.example.quiz.scenario.TestServiceScenarios;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TestServiceTest {

    @Mock
    private TestRepository testRepository;

    @Mock
    private OptionService optionService;

    @Mock
    private TestMapper testMapper;

    @Mock
    private StudentService studentService;

    @Mock
    private QuestionService questionService;

    @Mock
    private ParticipationService participationService;

    @InjectMocks
    private TestService testService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @org.junit.jupiter.api.Test
    void shouldReturnAllTests_whenPageableIsProvided() {
        // given
        var pageable = mock(Pageable.class);
        var expectedPage = mock(Page.class);
        when(testRepository.findAllTests(pageable)).thenReturn(expectedPage);

        // when
        var result = testService.getAllTest(pageable);

        // then
        assertThat(result).isEqualTo(expectedPage);
        verify(testRepository, times(1)).findAllTests(pageable);
    }

    @org.junit.jupiter.api.Test
    void shouldReturnTestResult_whenTestAndStudentExist() {
        // given
        var testId = 1L;
        var studentId = 1L;
        var participation = List.of(TestServiceScenarios.getFirstParticipation(),
                TestServiceScenarios.getLastParticipation(),
                TestServiceScenarios.getNotSelectedParticipation());

        when(participationService.getTestResultByTestIdAndStudentId(testId, studentId)).thenReturn(participation);

        // when
        var result = testService.getResult(testId, studentId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTestName()).isEqualTo("Sample Test");
        assertThat(result.getStudentFirstName()).isEqualTo("John");
        assertThat(result.getStudentLastName()).isEqualTo("Doe");
        assertThat(result.getStudentNumber()).isEqualTo("123456");
        assertThat(result.getQuestions()).isNotEmpty();
        verify(participationService).getTestResultByTestIdAndStudentId(testId, studentId);
    }

    @org.junit.jupiter.api.Test
    void shouldThrowTestNotFoundException_whenTestDoesNotExist() {
        // given
        var testId = 1L;
        when(testRepository.getTestById(testId)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> testService.getTest(testId))
                .isInstanceOf(TestNotFoundException.class)
                .hasMessage(MessageSourceConfiguration.getMessage("test.not.found"));

        // then
        verify(testRepository, times(1)).getTestById(testId);
    }

    @org.junit.jupiter.api.Test
    void shouldReturnTestQuestionResponse_whenTestExists() {
        // given
        var testId = 1L;
        var test = mock(Test.class);
        var expectedResponse = mock(TestQuestionResponse.class);
        when(testRepository.getTestById(testId)).thenReturn(Optional.of(test));
        when(testMapper.convertToResponse(test)).thenReturn(expectedResponse);

        // when
        var result = testService.getTest(testId);

        // then
        assertThat(result).isEqualTo(expectedResponse);
        verify(testRepository).getTestById(testId);
        verify(testMapper).convertToResponse(test);
    }

    @org.junit.jupiter.api.Test
    void shouldThrowTestAlreadySubmittedException_whenTestAlreadySubmitted() {
        // given
        var request = TestAnswerRequest.builder().testId(1L).studentId(1L).build();

        when(participationService.getByTestIdAndStudentId(request.getTestId(), request.getStudentId()))
                .thenReturn(List.of(mock(Participation.class)));

        // when
        assertThatThrownBy(() -> testService.submitTestAnswer(request))
                .isInstanceOf(TestAlreadySubmittedException.class)
                .hasMessage(MessageSourceConfiguration.getMessage("test.already.submitted"));

        // then
        verify(participationService, times(1)).getByTestIdAndStudentId(request.getTestId(), request.getStudentId());
    }

    @org.junit.jupiter.api.Test
    void shouldSubmitTestAnswer_whenNoPreviousParticipation() {
        // given
        var request = TestAnswerRequest.builder().testId(1L).studentId(1L).build();
        var questionAnswerDto = QuestionAnswerDto.builder().questionId(1L).selectedOptionId(2L).build();
        request.setAnswers(List.of(questionAnswerDto));

        when(participationService.getByTestIdAndStudentId(request.getTestId(), request.getStudentId())).thenReturn(List.of());
        when(testRepository.getReferenceById(request.getTestId())).thenReturn(mock(Test.class));
        when(studentService.getReferenceById(request.getStudentId())).thenReturn(mock(Student.class));
        when(questionService.getReferenceById(questionAnswerDto.getQuestionId())).thenReturn(mock(Question.class));
        when(optionService.getReferenceById(questionAnswerDto.getSelectedOptionId())).thenReturn(mock(Option.class));

        // when
        var result = testService.submitTestAnswer(request);

        // then
        assertThat(result).isEqualTo(MessageSourceConfiguration.getMessage("test.submitted"));
        verify(participationService, times(1)).saveAll(anyList());
    }

    @org.junit.jupiter.api.Test
    void shouldSubmitTestAnswer_whenNoSelectedOption() {
        // given
        var request = TestAnswerRequest.builder().testId(1L).studentId(1L).build();
        var questionAnswerDto = QuestionAnswerDto.builder().questionId(1L).build();
        request.setAnswers(List.of(questionAnswerDto));

        when(participationService.getByTestIdAndStudentId(request.getTestId(), request.getStudentId())).thenReturn(List.of());
        when(testRepository.getReferenceById(request.getTestId())).thenReturn(mock(Test.class));
        when(studentService.getReferenceById(request.getStudentId())).thenReturn(mock(Student.class));
        when(questionService.getReferenceById(questionAnswerDto.getQuestionId())).thenReturn(mock(Question.class));
        when(optionService.getReferenceById(null)).thenReturn(null);

        // when
        var result = testService.submitTestAnswer(request);

        // then
        assertThat(result).isEqualTo(MessageSourceConfiguration.getMessage("test.submitted"));
        verify(participationService).saveAll(anyList());
    }
}
