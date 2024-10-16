package org.example.quiz.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseQuestionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5117700673577509578L;

    private Long id;

    private String content;

}
