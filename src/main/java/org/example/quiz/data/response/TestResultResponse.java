package org.example.quiz.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quiz.data.dto.QuestionResultDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestResultResponse {

    private String testName;

    private String studentFirstName;

    private String studentLastName;

    private String studentNumber;

    private Long correctAnswerCount;

    private Long wrongAnswerCount;

    private Integer questionCount;

    private List<QuestionResultDto> questions;
}
