package pl.ania.service;

import org.springframework.stereotype.Service;
import pl.ania.domain.Comment;
import pl.ania.domain.Grade;
import pl.ania.domain.Student;
import pl.ania.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public String addStudent(String lastName) {
        Student student = new Student(UUID.randomUUID().toString(), lastName, null, null);
        studentRepository.save(student);
        return student.getId();
    }

    public void deleteStudent(String id) {
        studentRepository.delete(id);
    }

    public Student findById(String id) {
        return studentRepository.findOne(id);
    }

    public List<Student> showAllStudents() {
        return studentRepository.findAll();
    }

    public List<Grade> showGrades(String id) {
        return studentRepository.findOne(id).getGrades();
    }

    public void addGrade(String id, int value) {
        Student oldStudent = findById(id);
        List<Grade> grades = findById(id).getGrades();
        if (grades == null) {
            grades = new ArrayList<>();
        }
        grades.add(new Grade(value));

        Student student = new Student(oldStudent.getId(),
                oldStudent.getLastName(), grades, oldStudent.getComments());
        studentRepository.save(student);
    }

    public List<Comment> showComment(String id) {
        return studentRepository.findOne(id).getComments();
    }

    public void addComment(String id, String body) {
        Student oldStudent = findById(id);
        List<Comment> comments = findById(id).getComments();
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(new Comment(body));

        Student student = new Student(oldStudent.getId(),
                oldStudent.getLastName(), oldStudent.getGrades(), comments);
        studentRepository.save(student);
    }

    public void addGrades(String id, List<String> values) {
        Student oldStudent = findById(id);
        List<Grade> grades = oldStudent.getGrades();
        if (grades == null){
            grades = new ArrayList<>();
        }

        for (int i = 0; i < values.size(); i++) {
           int value =  Integer.parseInt(values.get(i));
            grades.add(new Grade(value));
        }
    }
}
