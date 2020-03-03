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
import rva.jpa.Projekat;
import rva.rep.ProjekatRepository;


@CrossOrigin
@Api(tags = {"Projekat CRUD operacije"})
@RestController
public class ProjekatRestController {

	@Autowired
	private ProjekatRepository ProjekatRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("/projekat")
	@ApiOperation(value = "Vraca kolekciju svih fakulteta iz baze podataka")
	public Collection<Projekat> getProjekti() {
		return ProjekatRepository.findAll();
	}
	
	@GetMapping("/projekat/{id}")
	@ApiOperation(value = "Vraca projekat iz baze podataka cija je id vrednost prosledjena kao path varijabla")
	public Projekat getProjekat(@PathVariable Integer id) {
		return ProjekatRepository.getOne(id);
	}
	
	@GetMapping("/projekatNaziv/{naziv}")
	@ApiOperation(value = "Vraca kolekciju svih projekta iz baze podataka koji u nazivu sadrze string prosledjen kao path varijabla")
	public Collection<Projekat> findByNaziv(@PathVariable String naziv) {
		return ProjekatRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@DeleteMapping("/projekat/{id}") 
	@ApiOperation(value = "Brise projekat iz baze podataka cija je id vrednost prosledjena kao path varijabla")
	public ResponseEntity<HttpStatus> deleteProjekat(@PathVariable Integer id) {
		if (ProjekatRepository.existsById(id)) {
			ProjekatRepository.deleteById(id);

			if (id == -100)
				jdbcTemplate.execute("INSERT INTO \"projekat\"(\"id\", \"naziv\", \"opis\", \"oznaka\")"
						+ "VALUES(-100, 'naziv TEST', 'opis TEST', 'oznaka TEST');");

			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/projekat")
	@ApiOperation(value = "Upisuje projekat u bazu podataka")
	public ResponseEntity<HttpStatus> insertProjekat(@RequestBody Projekat projekat) {
		ProjekatRepository.save(projekat);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/projekat")
	@ApiOperation(value = "Modifikuje postojeci projekat u bazi podataka")
	public ResponseEntity<HttpStatus> updateProjekat(@RequestBody Projekat projekat) {
		if (ProjekatRepository.existsById(projekat.getId()))
			ProjekatRepository.save(projekat);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
