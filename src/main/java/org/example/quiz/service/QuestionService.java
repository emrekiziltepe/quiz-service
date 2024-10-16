package org.example.quiz.service;

import lombok.RequiredArgsConstructor;
import org.example.quiz.domain.entity.Question;
import org.example.quiz.domain.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question getReferenceById(Long id) {
        return questionRepository.getReferenceById(id);
    }
}
