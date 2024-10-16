package org.example.quiz.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OptionResultDto extends OptionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2874218100386365951L;

    private Boolean selected;

    private Boolean correct = Boolean.FALSE;

}
