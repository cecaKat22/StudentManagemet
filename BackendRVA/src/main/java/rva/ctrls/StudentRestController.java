package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Grupa;
import rva.jpa.Student;
import rva.rep.GrupaRepository;
import rva.rep.StudentRepository;


@CrossOrigin
@Api(tags = {"Student CRUD operacije"})
@RestController
public class StudentRestController {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private GrupaRepository grupaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@GetMapping("/student")
	@ApiOperation(value = "Vraca kolekciju svih sudenata iz baze podataka")
	public Collection<Student> getStudenti() {
		return studentRepository.findAll();
	}
	
	@GetMapping(value = "studentGrupa/{id}")
	public Collection<Student> studentPoGrupi(@PathVariable("id") Integer id){
		Grupa g=grupaRepository.getOne(id);
		return studentRepository.findByGrupa(g);
	}
	
	
	@GetMapping("/student/{id}")
	@ApiOperation(value = "Vraca studenta iz baze podataka cija je id vrednost prosledjena kao path varijabla")
	public Student getStudent(@PathVariable Integer id) {
		return studentRepository.getOne(id);
	}
	
	
	@GetMapping("/studentIndeks/{brojIndeksa}")
	@ApiOperation(value = "Vraca kolekciju svih studenata iz baze podataka ciji je broj indeksa prosledjen kao path varijabla")
	public Collection<Student> findByBrojIndeksa(@PathVariable String brojIndeksa) {
		return studentRepository.findBybrojIndeksaContainingIgnoreCase(brojIndeksa);
	}
	
	@ApiOperation(value = "Brise studenta iz baze podataka ciji je id vrednost prosledjena kao path varijabla")
	@DeleteMapping("/student/{id}") 
	public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Integer id) {
		if (studentRepository.existsById(id)) {
			studentRepository.deleteById(id);

			if (id == -100)
				jdbcTemplate.execute("INSERT INTO \"student\"(\"id\", \"ime\", \"prezime\", \"broj_indeksa\", \"grupa\", \"projekat\")"
						+ "VALUES(-100, 'Ime TEST', 'Prezime TEST', 'Broj indeksa TEST', 'Grupa TEST', 'Projekat TEST');");

			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/student")
	@ApiOperation(value = "Upisuje studenta u bazu podataka")
	public ResponseEntity<HttpStatus> insertStudent(@RequestBody Student student) {
		studentRepository.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/student")
	@ApiOperation(value = "Modifikuje postojeceg studenta u bazi podataka")
	public ResponseEntity<HttpStatus> updateStudent(@RequestBody Student student) {
		if (studentRepository.existsById(student.getId()))
			studentRepository.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
