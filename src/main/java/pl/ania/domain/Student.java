package pl.ania.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "students")
public class Student {

    private String id;

    private String lastName;

    private List<Grade> grades;

    private List<Comment> comments;

    public Student(String id, String lastName, List<Grade> grades, List<Comment> comments) {
        this.id = id;
        this.lastName = lastName;
        this.grades = grades;
        this.comments = comments;
    }


    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
