package org.example.quiz.mapper;

import org.example.quiz.data.dto.OptionDto;
import org.example.quiz.data.dto.QuestionDto;
import org.example.quiz.data.response.TestQuestionResponse;
import org.example.quiz.data.response.TestResultResponse;
import org.example.quiz.domain.entity.Option;
import org.example.quiz.domain.entity.Participation;
import org.example.quiz.domain.entity.Question;
import org.example.quiz.domain.entity.Test;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TestMapper {

    TestQuestionResponse convertToResponse(Test test);

    List<QuestionDto> convertToQuestionDtoList(List<Question> questions);

    QuestionDto convertToQuestionDto(Question question);

    List<OptionDto> convertToOptionDtoList(List<Option> options);

    OptionDto convertToOptionDto(Option option);

}
