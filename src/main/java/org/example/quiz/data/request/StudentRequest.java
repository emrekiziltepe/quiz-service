package org.example.quiz.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    @NotBlank(message = "student.first.name.not.blank")
    @Size(max = 50, message = "student.first.name.size.exceeded")
    private String firstName;

    @NotBlank(message = "student.last.name.not.blank")
    @Size(max = 50, message = "student.last.name.size.exceeded")
    private String lastName;

}
