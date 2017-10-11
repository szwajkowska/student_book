package pl.ania.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.ania.domain.Student;

public interface StudentRepository extends MongoRepository<Student, String> {

}
