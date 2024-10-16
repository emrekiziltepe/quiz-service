package org.example.quiz.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quiz.data.dto.QuestionDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionResponse {

    private String name;

    private List<QuestionDto> questions;

}
