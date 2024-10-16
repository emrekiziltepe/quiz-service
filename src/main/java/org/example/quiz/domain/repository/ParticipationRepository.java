package org.example.quiz.domain.repository;

import org.example.quiz.domain.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    @Query("""
            select p
            from Participation p
            where p.test.id = :testId and p.student.id = :studentId
            """)
    List<Participation> findTestResultByTestIdAndStudentId(@Param("testId") Long testId,
                                                           @Param("studentId") Long studentId);

}
