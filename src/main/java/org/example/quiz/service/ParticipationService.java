package org.example.quiz.service;

import lombok.RequiredArgsConstructor;
import org.example.quiz.domain.entity.Participation;
import org.example.quiz.domain.repository.ParticipationRepository;
import org.example.quiz.exception.business.ParticipationNotFoundException;
import org.example.quiz.infrastructure.config.MessageSourceConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.quiz.constant.MessageConstant.PARTICIPATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    public void saveAll(List<Participation> participation) {
        participationRepository.saveAll(participation);
    }

    public List<Participation> getTestResultByTestIdAndStudentId(Long testId, Long studentId) {
        var participation = getByTestIdAndStudentId(testId, studentId);
        if (participation.isEmpty()) {
            throw new ParticipationNotFoundException(MessageSourceConfiguration.getMessage(PARTICIPATION_NOT_FOUND));
        }

        return participation;
    }

    public List<Participation> getByTestIdAndStudentId(Long testId, Long studentId){
        return participationRepository.findTestResultByTestIdAndStudentId(testId, studentId);
    }

}
