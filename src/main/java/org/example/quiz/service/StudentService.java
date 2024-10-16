package org.example.quiz.service;

import lombok.RequiredArgsConstructor;
import org.example.quiz.data.request.StudentRequest;
import org.example.quiz.domain.entity.Student;
import org.example.quiz.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private static final Random random = new Random();

    public void addStudent(StudentRequest studentRequest) {
        var number = random.ints(10, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        var student = Student.builder()
                .firstName(studentRequest.getFirstName())
                .lastName(studentRequest.getLastName())
                .number(number)
                .build();
        studentRepository.save(student);
    }

    public Student getReferenceById(Long id) {
        return studentRepository.getReferenceById(id);
    }
}
