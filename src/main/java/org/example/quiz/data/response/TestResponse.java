package org.example.quiz.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestResponse {

    private Long id;

    private String name;

    private Boolean isParticipated;

}
