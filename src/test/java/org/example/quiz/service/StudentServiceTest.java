package org.example.quiz.service;

import org.example.quiz.data.request.StudentRequest;
import org.example.quiz.domain.entity.Student;
import org.example.quiz.domain.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private Random random;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddStudent_whenStudentRequestIsValid() {
        // given
        var studentRequest = new StudentRequest();
        studentRequest.setFirstName("John");
        studentRequest.setLastName("Doe");

        var randomInts = IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
        when(random.ints(10, 0, 10)).thenReturn(randomInts);

        // when
        studentService.addStudent(studentRequest);

        // then
        verify(studentRepository).save(argThat(student ->
                student.getFirstName().equals("John") &&
                student.getLastName().equals("Doe")
        ));
    }

    @Test
    void shouldReturnStudent_whenIdIsValid() {
        // given
        var studentId = 1L;
        var student = Student.builder()
                .id(studentId)
                .firstName("Jane")
                .lastName("Smith")
                .number("9876543210")
                .build();
        when(studentRepository.getReferenceById(studentId)).thenReturn(student);

        // when
        var result = studentService.getReferenceById(studentId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(studentId);
        assertThat(result.getFirstName()).isEqualTo("Jane");
        assertThat(result.getLastName()).isEqualTo("Smith");
        verify(studentRepository, times(1)).getReferenceById(studentId);
    }

    @Test
    void shouldThrowException_whenStudentNotFound() {
        // given
        var studentId = 99L;
        when(studentRepository.getReferenceById(studentId)).thenThrow(new IllegalArgumentException("Student not found"));

        // when / then
        var exception = assertThrows(IllegalArgumentException.class, () -> studentService.getReferenceById(studentId));

        assertThat(exception.getMessage()).isEqualTo("Student not found");
        verify(studentRepository, times(1)).getReferenceById(studentId);
    }
}
