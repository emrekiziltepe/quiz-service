package org.example.quiz.service;

import lombok.RequiredArgsConstructor;
import org.example.quiz.domain.entity.Option;
import org.example.quiz.domain.repository.OptionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    public Option getReferenceById(Long id) {
        return optionRepository.getReferenceById(id);
    }
}
