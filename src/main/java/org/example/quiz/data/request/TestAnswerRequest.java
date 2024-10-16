package org.example.quiz.data.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quiz.data.dto.QuestionAnswerDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestAnswerRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private Long testId;

    @NotEmpty
    private List<QuestionAnswerDto> answers;
}
