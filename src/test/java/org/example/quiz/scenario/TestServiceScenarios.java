package org.example.quiz.scenario;

import lombok.experimental.UtilityClass;
import org.example.quiz.domain.entity.Option;
import org.example.quiz.domain.entity.Participation;
import org.example.quiz.domain.entity.Question;
import org.example.quiz.domain.entity.Student;
import org.example.quiz.domain.entity.Test;

import java.util.List;

@UtilityClass
public class TestServiceScenarios {

    public Participation getFirstParticipation() {
        return Participation.builder()
                .student(getStudent())
                .test(getTest())
                .question(Question.builder()
                        .options(List.of(Option.builder().id(1L).correct(true).build(),
                                Option.builder().id(2L).correct(false).build()))
                        .build())
                .selectedOption(Option.builder().id(1L).build())
                .build();
    }

    public Participation getLastParticipation() {
        return Participation.builder()
                .student(getStudent())
                .test(getTest())
                .question(Question.builder()
                        .options(List.of(Option.builder().id(3L).correct(false).build(),
                                Option.builder().id(4L).correct(true).build()))
                        .build())
                .selectedOption(Option.builder().id(3L).build())
                .build();
    }

    public Participation getNotSelectedParticipation() {
        return Participation.builder()
                .student(getStudent())
                .test(getTest())
                .question(Question.builder()
                        .options(List.of(Option.builder().id(1L).correct(true).build(),
                                Option.builder().id(2L).correct(false).build()))
                        .build())
                .build();
    }

    private Student getStudent(){
        return Student.builder().firstName("John").lastName("Doe").number("123456").build();
    }

    private Test getTest(){
        return Test.builder().name("Sample Test").build();
    }
}
