package org.example.quiz.domain.repository;

import org.example.quiz.data.response.TestResponse;
import org.example.quiz.domain.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("""
            select t
            from Test t
            inner join fetch Question q on q.test.id = t.id
            inner join fetch Option o on o.question.id = q.id
            where t.id = :id
            """)
    Optional<Test> getTestById(@Param("id") Long id);

    @Query("""
            select new org.example.quiz.data.response.TestResponse(
                        t.id,
                        t.name,
                        case when count(p.id) > 0 then true else false end)
            from Test t
            left join Participation p on p.test.id = t.id
            group by t.id
            """)
    Page<TestResponse> findAllTests(Pageable pageable);

    @Query("""
            select t
            from Test t
            inner join Participation p on p.test.id = t.id
            inner join p.student s
            where t.id = :testId
            """)
    Optional<Test> findWithParticipationById(@Param("testId") Long testId);

}
