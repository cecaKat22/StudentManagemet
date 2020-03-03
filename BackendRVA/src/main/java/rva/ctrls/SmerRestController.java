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
import rva.jpa.Smer;
import rva.rep.SmerRepository;


@CrossOrigin
@Api(tags = {"Smer CRUD operacije"})
@RestController
public class SmerRestController {

	@Autowired
	private SmerRepository SmerRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("/smer")
	@ApiOperation(value = "Vraca kolekciju svih fakulteta iz baze podataka")
	public Collection<Smer> getSmerovi() {
		return SmerRepository.findAll();
	}
	
	@GetMapping("/smer/{id}")
	@ApiOperation(value = "Vraca smer iz baze podataka cija je id vrednost prosledjena kao path varijabla")
	public Smer getSmer(@PathVariable Integer id) {
		return SmerRepository.getOne(id);
	}
	
	@GetMapping("/smerNaziv/{naziv}")
	@ApiOperation(value = "Vraca kolekciju svih smerova iz baze podataka koji u nazivu sadrze string prosledjen kao path varijabla")
	public Collection<Smer> findByNaziv(@PathVariable String naziv) {
		return SmerRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@DeleteMapping("/smer/{id}") 
	@ApiOperation(value = "Brise smer iz baze podataka cija je id vrednost prosledjena kao path varijabla")
	public ResponseEntity<HttpStatus> deleteSmer(@PathVariable Integer id) {
		if (SmerRepository.existsById(id)) {
			SmerRepository.deleteById(id);

			if (id == -100)
				jdbcTemplate.execute("INSERT INTO \"smer\"(\"id\", \"naziv\", \"oznaka\")"
						+ "VALUES(-100, 'naziv TEST', 'oznaka TEST');");

			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/smer")
	@ApiOperation(value = "Upisuje smer u bazu podataka")
	public ResponseEntity<HttpStatus> insertSmer(@RequestBody Smer smer) {
		SmerRepository.save(smer);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/smer")
	@ApiOperation(value = "Modifikuje postojeci smer u bazi podataka")
	public ResponseEntity<HttpStatus> updateFakultet(@RequestBody Smer smer) {
		if (SmerRepository.existsById(smer.getId()))
			SmerRepository.save(smer);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
