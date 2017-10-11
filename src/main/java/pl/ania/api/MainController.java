package pl.ania.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ania.domain.Comment;
import pl.ania.domain.Grade;
import pl.ania.domain.Student;
import pl.ania.service.StudentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class MainController {

    private StudentService studentService;

    public MainController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> returnStudents() {
        return studentService.showAllStudents();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> returnStudentById(@PathVariable String id) {
        Student student = studentService.findById(id);
        if (student == null) {
            ResponseEntity<Student> build = new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
            return build;
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<Void> addStudent(@RequestBody String lastName) {
        String id = studentService.addStudent(lastName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/" + id));
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/students/{id}/grades")
    public List<Grade> showGrades(@PathVariable String id){
       return studentService.findById(id).getGrades();
    }

    @PostMapping("/students/{id}/grades")
    public ResponseEntity<Void> addGrade(@PathVariable String id, @RequestBody String value){
        studentService.addGrade(id, Integer.parseInt(value));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/" + id + "/grades"));
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/students/{id}/comments")
    public List<Comment> showComments(@PathVariable String id){
        return studentService.findById(id).getComments();
    }

    @PostMapping("/students/{id}/comments")
    public ResponseEntity<Void> addComment(@PathVariable String id, @RequestBody String body){
        studentService.addComment(id, body);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/" + id + "/comments"));
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/students/{id}/listOfGrades")
    public ResponseEntity<Void> addGrades(@PathVariable String id, @RequestBody List<String> values){
        studentService.addGrades(id, values);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/" + id + "/grades"));
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }
}
