package rva.ctrls;

import java.util.Collection;

import javax.transaction.Transactional;

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
import rva.rep.GrupaRepository;

@CrossOrigin
@Api(tags = {"Grupa CRUD operacije"})
@RestController
public class GrupaRestContoller {

	@Autowired
	private GrupaRepository grupaRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/grupa")
	@ApiOperation(value = "Vraca kolekciju svih grupa iz baze podataka")
	public Collection<Grupa> getGrupe() {
		return grupaRepository.findAll();
	}

	@GetMapping("/grupa/{id}")
	@ApiOperation(value = "Vraca grupa iz baze podataka cija je id vrednost prosledjena kao path varijabla")
	public Grupa getGrupa(@PathVariable("id") Integer id) {
		return grupaRepository.getOne(id);
	}

	@GetMapping("/grupaOznaka/{oznaka}")
	@ApiOperation(value = "Vraca kolekciju svih grupa iz baze podataka koji u nazivu sadrze string prosledjen kao path varijabla")
	public Collection<Grupa> getGrupaByOznaka(@PathVariable String oznaka) {
		return grupaRepository.findByOznakaContainingIgnoreCase(oznaka);
	}

	@Transactional
	@DeleteMapping("grupa/{id}")
	public ResponseEntity<Grupa> deleteGrupa(@PathVariable ("id") Integer id){
		if(!grupaRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		jdbcTemplate.execute("delete from student where grupa = "+id);	
		grupaRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// insert
	@PostMapping("/grupa")
	@ApiOperation(value = "Upisuje grupu u bazu podataka")
	public ResponseEntity<Grupa> insertGrupa(@RequestBody Grupa grupa) {
		grupaRepository.save(grupa);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// update
	@PutMapping("/grupa")
	@ApiOperation(value = "Modifikuje postojecu grupu u bazi podataka")
	public ResponseEntity<Grupa> updateGrupa(@RequestBody Grupa grupa) {
		if (!grupaRepository.existsById(grupa.getId()))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		grupaRepository.save(grupa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
