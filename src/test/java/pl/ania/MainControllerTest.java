package pl.ania;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.ania.domain.Comment;
import pl.ania.domain.Grade;
import pl.ania.domain.Student;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MainControllerTest extends ControllerTest {

    @Test
    public void shouldShowStudents() throws Exception {
        Student student1 = new Student("1", "Kowalski", Arrays.asList(new Grade(2), new Grade(3)),
                Arrays.asList(new Comment("a"), new Comment("b")));
        Student student2 = new Student("2", "Nowak", Arrays.asList(new Grade(4), new Grade(5)),
                Arrays.asList(new Comment("c"), new Comment("d")));

        studentRepository.save(student1);
        studentRepository.save(student2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students")
                        .accept(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("[{'id': '1', 'lastName': 'Kowalski', 'grades': [{'value':2}, {'value': 3}], " +
                                "'comments': [{'body':'a'}, {'body': 'b'}]}," +
                                "{'id': '2', 'lastName': 'Nowak', 'grades': [{'value':4}, {'value': 5}], " +
                                "'comments': [{'body':'c'}, {'body': 'd'}]}]"));

    }

    @Test
    public void shouldReturnStudentById() throws Exception {
        Student student1 = new Student("1", "Kowalski", Arrays.asList(new Grade(2), new Grade(3)),
                Arrays.asList(new Comment("a"), new Comment("b")));
        Student student2 = new Student("2", "Nowak", Arrays.asList(new Grade(4), new Grade(5)),
                Arrays.asList(new Comment("c"), new Comment("d")));

        studentRepository.save(student1);
        studentRepository.save(student2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students/2")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{'id': '2', 'lastName': 'Nowak', 'grades': [{'value':4}, {'value': 5}], " +
                                "'comments': [{'body':'c'}, {'body': 'd'}]}"
                ));

        Assertions.assertThat(studentRepository.count()).isEqualTo(2);
    }

    @Test
    public void shouldAddStudent() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/students")
                        .content("Kowalski")

        )
                .andDo(print())
                .andExpect(status().isCreated());

        Assertions.assertThat(studentRepository.count()).isEqualTo(1);
        Assertions.assertThat(studentRepository.findAll().get(0).getLastName()).isEqualTo("Kowalski");

    }

    @Test
    public void shouldDeleteStudent() throws Exception {
        Student student1 = new Student("1", "Kowalski", Arrays.asList(new Grade(2), new Grade(3)),
                Arrays.asList(new Comment("a"), new Comment("b")));
        Student student2 = new Student("2", "Nowak", Arrays.asList(new Grade(4), new Grade(5)),
                Arrays.asList(new Comment("c"), new Comment("d")));

        studentRepository.save(student1);
        studentRepository.save(student2);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/students/1")
        );

        Assertions.assertThat(studentRepository.count()).isEqualTo(1);
        Assertions.assertThat(studentRepository.findAll().get(0).getLastName()).isEqualTo("Nowak");

    }

    @Test
    public void shouldShowGrade() throws Exception {
        Student student1 = new Student("1", "Kowalski", Arrays.asList(new Grade(2), new Grade(3)),
                Arrays.asList(new Comment("a"), new Comment("b")));

        studentRepository.save(student1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students/1/grades")
        )
                .andExpect(content().json(
                        "[{'value':2},{'value':3}]"
                ));
    }

    @Test
    public void shouldAddGrade() throws Exception {
        Student student1 = new Student("1", "Kowalski", Arrays.asList(new Grade(2), new Grade(3)),
                Arrays.asList(new Comment("a"), new Comment("b")));

        studentRepository.save(student1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/students/1/grades")
                        .content("5")
        )
                .andDo(print())
                .andExpect(status().isCreated());

        Assertions.assertThat(studentRepository.findOne("1").getGrades().size()).isEqualTo(3);
        Assertions.assertThat(studentRepository.findOne("1").getGrades().get(2).getValue()).isEqualTo(5);

    }

    @Test
    public void shouldShowComments() throws Exception {
        Student student1 = new Student("1", "Kowalski", Arrays.asList(new Grade(2), new Grade(3)),
                Arrays.asList(new Comment("a"), new Comment("b")));


        studentRepository.save(student1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students/1/comments")
        )
                .andExpect(content().json(
                        "[{'body':'a'},{'body':'b'}]"
                ));
    }

    @Test
    public void shouldAddComments() throws Exception {
        Student student1 = new Student("1", "Kowalski", Arrays.asList(new Grade(2), new Grade(3)),
                Arrays.asList(new Comment("a"), new Comment("b")));

        studentRepository.save(student1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/students/1/comments")
                        .content("xxx")
        )
                .andDo(print())
                .andExpect(status().isCreated());

        Assertions.assertThat(studentRepository.findOne("1").getComments().size()).isEqualTo(3);
        Assertions.assertThat(studentRepository.findOne("1").getComments().get(2).getBody()).isEqualTo("xxx");
    }

//    @Test
//    public void shouldAddListOfGrades() throws Exception{
//        Student student1 = new Student("1", "Kowalski", Arrays.asList(new Grade(2), new Grade(3)),
//                Arrays.asList(new Comment("a"), new Comment("b")));
//
//        studentRepository.save(student1);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/students/1/listOfGrades")
//                .content(["1", "2"])
//        )
//    }

}

