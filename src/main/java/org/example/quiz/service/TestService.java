package org.example.quiz.service;

import lombok.RequiredArgsConstructor;
import org.example.quiz.data.dto.OptionResultDto;
import org.example.quiz.data.dto.QuestionAnswerDto;
import org.example.quiz.data.dto.QuestionResultDto;
import org.example.quiz.data.request.TestAnswerRequest;
import org.example.quiz.data.response.TestQuestionResponse;
import org.example.quiz.data.response.TestResponse;
import org.example.quiz.data.response.TestResultResponse;
import org.example.quiz.domain.entity.Option;
import org.example.quiz.domain.entity.Participation;
import org.example.quiz.domain.repository.TestRepository;
import org.example.quiz.enums.Caches;
import org.example.quiz.exception.business.TestAlreadySubmittedException;
import org.example.quiz.exception.business.TestNotFoundException;
import org.example.quiz.infrastructure.config.MessageSourceConfiguration;
import org.example.quiz.mapper.TestMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.example.quiz.constant.MessageConstant.TEST_ALREADY_SUBMITTED;
import static org.example.quiz.constant.MessageConstant.TEST_SUBMITTED;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    private final OptionService optionService;

    private final TestMapper testMapper;

    private final StudentService studentService;

    private final QuestionService questionService;

    private final ParticipationService participationService;

    public Page<TestResponse> getAllTest(Pageable pageable) {
        return testRepository.findAllTests(pageable);
    }

    @Cacheable(value = Caches.ONE_DAY, key = "{#root.targetClass, #root.methodName, #id, #studentId}",
            unless = "#result == null")
    @Transactional(readOnly = true)
    public TestResultResponse getResult(Long id, Long studentId) {
        var participation = participationService.getTestResultByTestIdAndStudentId(id, studentId);
        var student = participation.getFirst().getStudent();
        var test = participation.getFirst().getTest();
        var questionList = getQuestionResults(participation);
        return TestResultResponse.builder()
                .testName(test.getName())
                .studentFirstName(student.getFirstName())
                .studentLastName(student.getLastName())
                .studentNumber(student.getNumber())
                .questions(questionList)
                .questionCount(questionList.size())
                .correctAnswerCount(getAnswerCount(participation, true))
                .wrongAnswerCount(getAnswerCount(participation, false))
                .build();
    }

    private static long getAnswerCount(List<Participation> participation, Boolean isCorrect) {
        return participation.stream()
                .filter(p -> Objects.nonNull(p.getSelectedOption()) &&
                             Objects.equals(p.getSelectedOption().getCorrect(), isCorrect))
                .count();
    }

    private static List<QuestionResultDto> getQuestionResults(List<Participation> participation) {
        return participation.stream().map(p -> {
            var question = p.getQuestion();
            var options = getOptionResults(p);
            return QuestionResultDto.builder()
                    .id(question.getId())
                    .content(question.getContent())
                    .options(options)
                    .build();
        }).collect(Collectors.toList());
    }

    private static List<OptionResultDto> getOptionResults(Participation p) {
        return p.getQuestion().getOptions().stream().map(option ->
                OptionResultDto.builder()
                        .id(option.getId())
                        .content(option.getContent())
                        .correct(option.getCorrect())
                        .selected(Objects.nonNull(p.getSelectedOption()) ? p.getSelectedOption().getId() == option.getId() : null)
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TestQuestionResponse getTest(Long id) {
        var test = testRepository.getTestById(id).orElseThrow(() -> new TestNotFoundException(MessageSourceConfiguration.getMessage("test.not.found")));
        return testMapper.convertToResponse(test);
    }

    public String submitTestAnswer(TestAnswerRequest request) {
        var participation = participationService.getByTestIdAndStudentId(request.getTestId(), request.getStudentId());
        if (!participation.isEmpty()) {
            throw new TestAlreadySubmittedException(MessageSourceConfiguration.getMessage(TEST_ALREADY_SUBMITTED));
        }
        var newParticipation = request.getAnswers().stream()
                .map(testAnswer -> Participation.builder()
                        .test(testRepository.getReferenceById(request.getTestId()))
                        .student(studentService.getReferenceById(request.getStudentId()))
                        .question(questionService.getReferenceById(testAnswer.getQuestionId()))
                        .selectedOption(getSelectedOption(testAnswer))
                        .build())
                .toList();

        participationService.saveAll(newParticipation);

        return MessageSourceConfiguration.getMessage(TEST_SUBMITTED);
    }

    private Option getSelectedOption(QuestionAnswerDto testAnswer) {
        return Objects.nonNull(testAnswer.getSelectedOptionId())
                ? optionService.getReferenceById(testAnswer.getSelectedOptionId()) : null;
    }

}
